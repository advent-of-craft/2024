package usecases

import domain.core.Error
import domain.core.Error.anError
import domain.{Toy, ToyRepository}
import domain.core.Extensions.let

import scala.util.Either

class ToyDeliveryUseCase(private val repository: ToyRepository) {
  def handle(deliverToy: DeliverToy): Either[Error, Unit] =
    repository.findByName(deliverToy.desiredToy)
      .toRight(errorFor(deliverToy))
      .flatMap(toy => reduceStock(toy))
      .map(_ => ())

  private def reduceStock(toy: Toy): Either[Error, Toy] =
    toy.reduceStock()
      .let(_ => repository.save(toy))
      .map(_ => toy)

  private def errorFor(deliverToy: DeliverToy): Error =
    anError(s"Oops we have a problem... we have not built the toy: ${deliverToy.desiredToy}")
}