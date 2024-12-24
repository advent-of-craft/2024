package doubles

import domain.core.{Aggregate, Event}
import domain.{Toy, ToyRepository}

import java.util.UUID
import scala.collection.mutable

class InMemoryToyRepository extends ToyRepository {
  private val toys: mutable.Map[UUID, Toy] = mutable.Map.empty
  private val events: mutable.ListBuffer[Event] = mutable.ListBuffer.empty
  
  override def findByName(toyName: String): Option[Toy] =
    toys.values.find(_.getName() == toyName)

  override def findById(id: UUID): Option[Toy] = toys.get(id)

  override def save(aggregate: Toy): Unit = {
    events.clear()
    toys(aggregate.id) = aggregate
    events ++= (aggregate.asInstanceOf[Aggregate].getUncommittedEvents())
    aggregate.asInstanceOf[Aggregate].clearUncommittedEvents()
  }

  def raisedEvents: List[Event] = events.toList
}