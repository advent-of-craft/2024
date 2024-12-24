package doubles

import arrow.core.Option
import arrow.core.toOption
import domain.Toy
import domain.ToyRepository
import domain.core.Aggregate
import domain.core.Event
import java.util.*

class InMemoryToyRepository : ToyRepository {
    private val toys: MutableMap<UUID, Toy> = HashMap()
    private var raisedEvents: MutableList<Event> = mutableListOf()

    override fun findByName(toyName: String): Option<Toy> = toys.values.firstOrNull { it.name() == toyName }.toOption()
    override fun findById(id: UUID): Option<Toy> = toys[id].toOption()

    override fun save(aggregate: Toy) {
        raisedEvents.clear()
        toys[aggregate.id] = aggregate
        raisedEvents.addAll(0, (aggregate as Aggregate).getUncommittedEvents())
        (aggregate as Aggregate).clearUncommittedEvents()
    }

    fun raisedEvents(): List<Event> = raisedEvents
}
