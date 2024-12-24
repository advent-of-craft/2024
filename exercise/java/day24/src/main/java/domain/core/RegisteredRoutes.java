package domain.core;

import io.vavr.collection.Map;
import io.vavr.collection.HashMap;

import java.util.function.Consumer;

public class RegisteredRoutes {
    private Map<String, Consumer<Event>> routes = HashMap.empty();

    public void dispatch(Event event) {
        routes.get(event.getClass().getName()).get().accept(event);
    }

    public void register(String eventType, Consumer<Event> apply) {
        routes = routes.put(eventType, apply);
    }
}