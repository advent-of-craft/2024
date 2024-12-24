package domain.core;

import io.vavr.collection.Seq;

import java.util.UUID;

public interface Aggregate {
    UUID getId();

    int getVersion();

    void applyEvent(Event event);

    Seq<Event> getUncommittedEvents();

    void clearUncommittedEvents();
}
