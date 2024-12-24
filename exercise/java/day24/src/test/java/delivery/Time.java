package delivery;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.function.Supplier;

public class Time {
    public static final LocalDateTime NOW = LocalDateTime.of(2024, Month.DECEMBER, 24, 23, 30, 45).atOffset(ZoneOffset.UTC).toLocalDateTime();
    public static final Supplier<LocalDateTime> PROVIDER = () -> NOW;
}