package domain.core

import java.time.LocalDateTime
import java.util.UUID
import scala.collection.mutable.ListBuffer

abstract class EventSourcedAggregate(timeProvider: () => LocalDateTime) extends Aggregate {
  private val uncommittedEvents: ListBuffer[Event] = ListBuffer.empty
  private val registeredRoutes: RegisteredRoutes = new RegisteredRoutes()
  protected var _id: UUID = UUID.randomUUID()
  private var _version: Int = 0

  def id: UUID = _id

  override def getUncommittedEvents(): List[Event] = uncommittedEvents.toList

  this.registerRoutes()

  override def clearUncommittedEvents(): Unit = uncommittedEvents.clear()

  override def version(): Int = _version

  protected def registerRoutes(): Unit

  protected def registerEventRoute[E <: Event](eventType: Class[E], func: E => Unit): Unit =
    registeredRoutes.register(eventType.getName, event => func(eventType.cast(event)))

  protected def raiseEvent(event: Event): Unit = {
    applyEvent(event)
    uncommittedEvents += event
  }

  override def applyEvent(event: Event): Unit = {
    registeredRoutes.dispatch(event)
    _version += 1
  }

  protected def time(): LocalDateTime = timeProvider()
}