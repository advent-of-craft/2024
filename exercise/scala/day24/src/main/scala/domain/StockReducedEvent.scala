package domain

import domain.core.Event
import java.time.LocalDateTime
import java.util.UUID

case class StockReducedEvent(
                              override val id: UUID,
                              override val date: LocalDateTime,
                              toyName: String,
                              newStock: StockUnit
                            ) extends Event
