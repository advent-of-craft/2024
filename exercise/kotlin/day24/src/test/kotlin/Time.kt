import java.time.LocalDateTime
import java.time.LocalDateTime.of
import java.time.Month
import java.time.ZoneOffset

object Time {
    val Now: LocalDateTime = of(2024, Month.DECEMBER, 24, 23, 30, 45)
        .atOffset(ZoneOffset.UTC)
        .toLocalDateTime()
    
    val TimeProvider: () -> LocalDateTime = { Now }
}
