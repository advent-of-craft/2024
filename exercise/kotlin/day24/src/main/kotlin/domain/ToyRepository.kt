package domain

import arrow.core.Option
import domain.core.Repository

interface ToyRepository : Repository<Toy> {
    fun findByName(toyName: String): Option<Toy>
}
