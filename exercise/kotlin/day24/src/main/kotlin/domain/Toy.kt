package domain

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import domain.core.Error
import domain.core.EventSourcedAggregate
import java.time.LocalDateTime
import java.util.*

class Toy private constructor(
    timeProvider: () -> LocalDateTime,
    private var name: String?,
    private var stock: StockUnit?
) : EventSourcedAggregate(timeProvider) {
    fun name(): String = name!!

    init {
        raiseEvent(ToyCreatedEvent(UUID.randomUUID(), timeProvider(), name!!, stock!!))
    }

    companion object {
        fun create(timeProvider: () -> LocalDateTime, name: String, stock: Int): Either<Error, Toy> =
            StockUnit.from(stock)
                .map { stockUnit ->
                    Toy(timeProvider, name, stockUnit)
                }
    }

    private fun apply(event: ToyCreatedEvent) {
        id = event.id
        name = event.name
        stock = event.stock
    }

    fun reduceStock(): Either<Error, Toy> {
        if (stock?.isSupplied() == false) return Error("No more $name in stock").left()
        raiseEvent(StockReducedEvent(id, time(), name!!, stock!!.decrease()))

        return right()
    }

    private fun apply(event: StockReducedEvent) {
        stock = event.newStock
    }

    override fun registerRoutes() {
        registerEventRoute(ToyCreatedEvent::class.java, this::apply)
        registerEventRoute(StockReducedEvent::class.java, this::apply)
    }
}
