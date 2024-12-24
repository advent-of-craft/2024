package domain.core

import java.util.function.Consumer

class RegisteredRoutes {
    private var routes: Map<String, Consumer<Event>> = emptyMap()

    fun dispatch(event: Event) {
        routes[event::class.java.name]?.accept(event)
    }

    fun register(eventType: String, apply: Consumer<Event>) {
        routes = routes + (eventType to apply)
    }
}
