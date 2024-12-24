package usecases;

import domain.Toy;
import domain.ToyRepository;
import domain.core.Error;
import domain.core.Unit;
import io.vavr.control.Either;

import static domain.core.Error.anError;

public class ToyDeliveryUseCase {
    private final ToyRepository repository;

    public ToyDeliveryUseCase(ToyRepository repository) {
        this.repository = repository;
    }

    public Either<Error, Unit> handle(DeliverToy deliverToy) {
        return repository
                .findByName(deliverToy.desiredToy())
                .toEither(() -> errorFor(deliverToy))
                .flatMap(this::reduceStock)
                .map(ignored -> Unit.DEFAULT);
    }

    private Either<Error, Toy> reduceStock(Toy toy) {
        return toy.reduceStock()
                .peek(repository::save)
                .peekLeft(left -> repository.save(toy))
                .map(updatedToy -> toy);
    }

    private static Error errorFor(DeliverToy deliverToy) {
        return anError("Oops we have a problem... we have not built the toy: " + deliverToy.desiredToy());
    }
}