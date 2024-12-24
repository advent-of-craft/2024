package domain;

import domain.core.Event;

import java.time.LocalDateTime;
import java.util.UUID;

public record StockReducedEvent(UUID id, LocalDateTime date, String toyName, StockUnit newStock) implements Event {
}