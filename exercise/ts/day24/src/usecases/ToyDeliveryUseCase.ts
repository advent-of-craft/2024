import {Either, left, right} from 'fp-ts/lib/Either';
import {Unit} from "../domain/core/Unit";
import {DeliverToy} from "./DeliverToy";
import {Toy} from "../domain/Toy";
import {pipe} from 'fp-ts/function';
import * as O from "fp-ts/Option";
import * as E from "fp-ts/Either";
import {DomainError} from "../domain/core/DomainError";
import {IToyRepository} from "../domain/IToyRepository";

export class ToyDeliveryUseCase {
    constructor(private repository: IToyRepository) {
    }

    handle(deliverToy: DeliverToy): Either<DomainError, Unit> {
        const foundToy = pipe(
            this.repository.findByName(deliverToy.desiredToy),
            option => pipe(
                option,
                O.match(
                    () => left(this.errorFor(deliverToy)),
                    some => right(some)
                )
            )
        );

        if (E.isRight(foundToy)) {
            return pipe(
                this.reduceStock(foundToy.right),
                E.match(
                    error => left(error),
                    () => right(Unit.default)
                )
            );
        }
        return foundToy;
    }

    private reduceStock(toy: Toy): Either<DomainError, Toy> {
        const updatedToy = toy.reduceStock();
        this.repository.save(toy);

        return updatedToy;
    }

    private errorFor(deliverToy: DeliverToy): DomainError {
        return DomainError.anError(`Oops we have a problem... we have not built the toy: ${deliverToy.desiredToy}`);
    }
}