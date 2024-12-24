package delivery.usecases;

import com.github.javafaker.Faker;
import delivery.Time;
import delivery.doubles.InMemoryToyRepository;
import domain.StockReducedEvent;
import domain.StockUnit;
import domain.Toy;
import domain.core.Unit;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import usecases.DeliverToy;
import usecases.ToyDeliveryUseCase;

import static delivery.ToyBuilder.toysInStock;
import static delivery.assertions.ToyAssertions.assertToy;
import static domain.core.Error.anError;
import static org.assertj.vavr.api.VavrAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ToyDeliveryTests {
    private final InMemoryToyRepository toyRepository;
    private final ToyDeliveryUseCase useCase;
    private final Faker faker;

    public ToyDeliveryTests() {
        this.toyRepository = new InMemoryToyRepository();
        this.useCase = new ToyDeliveryUseCase(toyRepository);
        this.faker = new Faker();
    }

    private Toy forASuppliedToy(int stock) {
        var toy = toysInStock(stock).build();
        toyRepository.save(toy);

        return toy;
    }

    @Nested
    class SuccessfullyWhen {
        @Test
        void toyAndUpdateStock() {
            var toy = forASuppliedToy(1);
            var command = new DeliverToy(faker.name().fullName(), toy.getName());

            var result = useCase.handle(command);

            assertThat(result).containsOnRight(Unit.DEFAULT);
            assertEquals(2, toy.getVersion());
            assertToy(toy).hasRaisedEvent(toyRepository,
                    new StockReducedEvent(
                            toy.getId(),
                            Time.NOW,
                            command.desiredToy(),
                            StockUnit.from(0)
                                    .getOrElseThrow(x -> new IllegalArgumentException())
                    ));
        }
    }

    @Nested
    class FailWhen {
        @Test
        void toyHasNotBeenBuilt() {
            var notBuiltToy = "Not a Bike";

            var result = useCase.handle(new DeliverToy(faker.name().fullName(), notBuiltToy));

            assertThat(result)
                    .containsOnLeft(anError("Oops we have a problem... we have not built the toy: Not a Bike"));
            assertThatNoEventHasBeenRaised();
        }

        @Test
        void toyIsNotSuppliedAnymore() {
            var toy = forASuppliedToy(0);

            var result = useCase.handle(new DeliverToy(faker.name().fullName(), toy.getName()));

            assertThat(result).containsOnLeft(anError("No more " + toy.getName() + " in stock"));
            assertThatNoEventHasBeenRaised();
        }

        private void assertThatNoEventHasBeenRaised() {
            assertThat(toyRepository.raisedEvents()).isEmpty();
        }
    }
}