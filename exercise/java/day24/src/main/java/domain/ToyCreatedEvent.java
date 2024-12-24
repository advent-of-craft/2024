package domain;

import domain.core.Event;

import java.time.LocalDateTime;
import java.util.UUID;

public record ToyCreatedEvent(UUID id, LocalDateTime date, String name, StockUnit stock) implements Event {
}