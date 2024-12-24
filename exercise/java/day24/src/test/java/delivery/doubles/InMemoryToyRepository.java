package delivery.doubles;

import domain.Toy;
import domain.ToyRepository;
import domain.core.Aggregate;
import domain.core.Event;
import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.collection.Seq;
import io.vavr.control.Option;

import java.util.UUID;

public class InMemoryToyRepository implements ToyRepository {
    private Map<UUID, Toy> toys = HashMap.empty();
    private Seq<Event> raisedEvents = List.empty();

    @Override
    public Option<Toy> findByName(String toyName) {
        return toys.filter(toy -> toy._2().getName().equals(toyName))
                .values()
                .headOption();
    }

    @Override
    public Option<Toy> findById(UUID id) {
        return toys.get(id);
    }

    @Override
    public void save(Toy toy) {
        raisedEvents = List.empty();
        toys = toys.put(toy.getId(), toy);

        ((Aggregate) toy).getUncommittedEvents()
                .forEach(event -> raisedEvents = raisedEvents.append(event));

        ((Aggregate) toy).clearUncommittedEvents();
    }

    public Seq<Event> raisedEvents() {
        return raisedEvents;
    }
}