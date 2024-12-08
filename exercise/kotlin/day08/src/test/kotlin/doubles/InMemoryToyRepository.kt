package doubles

import domain.Toy
import domain.ToyRepository

class InMemoryToyRepository : ToyRepository {
    private val toys = mutableListOf<Toy>()

    override fun findByName(name: String): Toy? = toys.firstOrNull { it.name == name }

    override fun save(toy: Toy) {
        toys.remove(toy)
        toys.add(toy)
    }
}
