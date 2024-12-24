package domain

import domain.core.{Error, EventSourcedAggregate}

import java.time.LocalDateTime
import java.util.UUID
import scala.util.{Either, Left, Right}

class Toy private(
                   timeProvider: () => LocalDateTime,
                   private var name: String,
                   private var stock: StockUnit
                 ) extends EventSourcedAggregate(timeProvider) {
  raiseEvent(ToyCreatedEvent(UUID.randomUUID(), timeProvider(), name, stock))

  def getName(): String = name

  def reduceStock(): Either[Error, Toy] = {
    if (!stock.isSupplied) return Left(Error(s"No more $name in stock"))
    raiseEvent(StockReducedEvent(id, time(), name, stock.decrease))
    Right(this)
  }

  override def registerRoutes(): Unit = {
    registerEventRoute(classOf[ToyCreatedEvent], this.apply)
    registerEventRoute(classOf[StockReducedEvent], this.apply)
  }

  private def apply(event: ToyCreatedEvent): Unit = {
    _id = event.id
    name = event.name
    stock = event.stock
  }

  private def apply(event: StockReducedEvent): Unit = stock = event.newStock
}

object Toy {
  def create(timeProvider: () => LocalDateTime, name: String, stock: Int): Either[Error, Toy] =
    StockUnit.from(stock).map(stockUnit => new Toy(timeProvider, name, stockUnit))
}