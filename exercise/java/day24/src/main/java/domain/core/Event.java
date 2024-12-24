package domain.core;

import java.time.LocalDateTime;
import java.util.UUID;

public interface Event {
    UUID id();

    default int getVersion() {
        return 1;
    }

    LocalDateTime date();
}