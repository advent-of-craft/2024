package delivery.assertions;

import delivery.doubles.InMemoryToyRepository;
import domain.Toy;
import domain.core.Event;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

import java.util.function.Consumer;

public class ToyAssertions extends AbstractAssert<ToyAssertions, Toy> {
    public ToyAssertions(Toy actual) {
        super(actual, ToyAssertions.class);
    }

    public static ToyAssertions assertToy(Toy actual) {
        return new ToyAssertions(actual);
    }

    public <E extends Event> ToyAssertions hasRaisedEvent(InMemoryToyRepository repository, E expectedEvent) {
        return assertThat(t -> {
            var raisedEvents = repository.raisedEvents();
            var lastEvent = raisedEvents.last();

            Assertions.assertThat(lastEvent)
                    .withFailMessage("Raised events should contain %s.", expectedEvent)
                    .isEqualTo(expectedEvent);
        });
    }

    private ToyAssertions assertThat(Consumer<Toy> assertion) {
        assertion.accept(actual);
        return this;
    }
}
