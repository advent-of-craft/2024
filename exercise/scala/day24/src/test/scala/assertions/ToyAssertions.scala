package assertions

import domain.Toy
import domain.core.Event
import doubles.InMemoryToyRepository
import org.scalatest.Assertions.fail

object ToyAssertions {
  def shouldHaveRaisedEvent(repository: InMemoryToyRepository, expectedEvent: Event): Unit = {
    val lastEvent = repository.raisedEvents.lastOption
    if (lastEvent.isEmpty || lastEvent.get != expectedEvent) {
      fail(s"Raised events should contain $expectedEvent. Actual: $lastEvent")
    }
  }
}