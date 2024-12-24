package domain.core

import arrow.core.Option
import java.util.*

interface Repository<A : EventSourcedAggregate> {
    fun findById(id: UUID): Option<A>
    fun save(aggregate: A)
}
