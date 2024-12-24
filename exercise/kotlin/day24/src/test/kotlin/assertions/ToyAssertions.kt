package assertions

import domain.Toy
import domain.core.Event
import doubles.InMemoryToyRepository
import kotlin.test.fail

fun Toy.shouldHaveRaisedEvent(repository: InMemoryToyRepository, expectedEvent: Event) =
    repository.raisedEvents()
        .lastOrNull()
        .let { lastEvent ->
            if (lastEvent == null || lastEvent != expectedEvent) {
                fail("Raised events should contain $expectedEvent. Actual: $lastEvent")
            }
        }