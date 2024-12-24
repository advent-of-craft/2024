package usecases

import arrow.core.Either
import arrow.core.flatMap
import domain.Toy
import domain.ToyRepository
import domain.core.Error
import domain.core.Error.Companion.anError

class ToyDeliveryUseCase(private val repository: ToyRepository) {
    fun handle(deliverToy: DeliverToy): Either<Error, Unit> =
        repository.findByName(deliverToy.desiredToy)
            .toEither { errorFor(deliverToy) }
            .flatMap { toy -> reduceStock(toy) }
            .map { }

    private fun reduceStock(toy: Toy): Either<Error, Toy> =
        toy.reduceStock()
            .tap { repository.save(it) }
            // we save even in case of failure
            .tapLeft { repository.save(toy) }
            .map { toy }

    private fun errorFor(deliverToy: DeliverToy): Error =
        anError("Oops we have a problem... we have not built the toy: ${deliverToy.desiredToy}")
}