import java.time.LocalDateTime.*
import java.time.{LocalDateTime, Month, ZoneOffset}

package object Time {
  val now: LocalDateTime = of(2024, Month.DECEMBER, 24, 23, 30, 45)
    .atOffset(ZoneOffset.UTC)
    .toLocalDateTime

  val timeProvider: () => LocalDateTime = () => now
}