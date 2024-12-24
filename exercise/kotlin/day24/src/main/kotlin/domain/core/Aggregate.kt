package domain.core

import java.util.*

interface Aggregate {
    val id: UUID
    val version: Int

    fun applyEvent(event: Event)
    fun getUncommittedEvents(): List<Event>
    fun clearUncommittedEvents()
}