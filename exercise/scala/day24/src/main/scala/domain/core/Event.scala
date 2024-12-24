package domain.core

import java.time.LocalDateTime
import java.util.UUID

trait Event {
  val id: UUID
  val version: Int = 1
  val date: LocalDateTime
}