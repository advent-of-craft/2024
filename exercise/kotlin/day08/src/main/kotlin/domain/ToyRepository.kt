package domain

interface ToyRepository {
    fun findByName(name: String): Toy?
    fun save(toy: Toy)
}