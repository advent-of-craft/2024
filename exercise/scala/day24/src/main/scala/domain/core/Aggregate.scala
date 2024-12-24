package domain.core

import java.util.UUID

trait Aggregate {
  def id: UUID

  def version(): Int

  def applyEvent(event: Event): Unit

  def getUncommittedEvents(): List[Event]

  def clearUncommittedEvents(): Unit
}