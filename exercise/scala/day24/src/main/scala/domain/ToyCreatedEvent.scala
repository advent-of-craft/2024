package domain

import domain.core.Event
import java.time.LocalDateTime
import java.util.UUID

case class ToyCreatedEvent(override val id: UUID,
                            override val date: LocalDateTime,
                            name: String,
                            stock: StockUnit
                          ) extends Event
