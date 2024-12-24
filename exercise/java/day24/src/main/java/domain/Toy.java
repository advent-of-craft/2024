package domain;

import domain.core.EventSourcedAggregate;
import io.vavr.control.Either;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.Supplier;

import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

public class Toy extends EventSourcedAggregate {
    @Getter
    private String name;
    private StockUnit stock;

    private Toy(Supplier<LocalDateTime> timeProvider, String name, StockUnit stock) {
        super(timeProvider);
        raiseEvent(new ToyCreatedEvent(UUID.randomUUID(), timeProvider.get(), name, stock));
    }

    public static Either<Error, Toy> create(Supplier<LocalDateTime> timeProvider, String name, int stock) {
        return StockUnit
                .from(stock)
                .map(stockUnit -> new Toy(timeProvider, name, stockUnit));
    }

    private void apply(ToyCreatedEvent event) {
        setId(event.id());
        name = event.name();
        stock = event.stock();
    }

    public Either<domain.core.Error, Toy> reduceStock() {
        if (!stock.isSupplied()) return left(new domain.core.Error("No more " + name + " in stock"));
        raiseEvent(new StockReducedEvent(getId(), time(), name, stock.decrease()));

        return right(this);
    }

    private void apply(StockReducedEvent event) {
        stock = event.newStock();
    }

    @Override
    protected void registerRoutes() {
        registerEventRoute(ToyCreatedEvent.class, this::apply);
        registerEventRoute(StockReducedEvent.class, this::apply);
    }
}