package domain.core

import java.util.UUID

trait Repository[A <: EventSourcedAggregate] {
  def findById(id: UUID): Option[A]

  def save(aggregate: A): Unit
}