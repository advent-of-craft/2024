package domain.core;

import io.vavr.collection.List;
import io.vavr.collection.Seq;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class EventSourcedAggregate implements Aggregate {
    private List<Event> uncommittedEvents = List.empty();
    private final RegisteredRoutes registeredRoutes = new RegisteredRoutes();
    private final Supplier<LocalDateTime> timeProvider;

    @Getter
    private UUID id;
    @Getter
    private int version;

    protected EventSourcedAggregate(Supplier<LocalDateTime> timeProvider) {
        this.timeProvider = timeProvider;
        registerRoutes();
    }

    protected void setId(UUID id) {
        this.id = id;
    }

    protected abstract void registerRoutes();

    @Override
    public void applyEvent(Event event) {
        registeredRoutes.dispatch(event);
        version++;
    }

    protected <E extends Event> void registerEventRoute(Class<E> eventType, Consumer<E> apply) {
        registeredRoutes.register(eventType.getName(), event -> apply.accept(eventType.cast(event)));
    }

    @Override
    public Seq<Event> getUncommittedEvents() {
        return uncommittedEvents;
    }

    @Override
    public void clearUncommittedEvents() {
        uncommittedEvents = List.empty();
    }

    protected void raiseEvent(Event event) {
        applyEvent(event);
        uncommittedEvents = uncommittedEvents.append(event);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || (obj instanceof Aggregate other && id.equals(other.getId()));
    }

    protected LocalDateTime time() {
        return timeProvider.get();
    }
}