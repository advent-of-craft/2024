import {faker} from '@faker-js/faker';
import {InMemoryToyRepository} from "../doubles/InMemoryToyRepository";
import {ToyDeliveryUseCase} from "../../src/usecases/ToyDeliveryUseCase";
import {Toy} from "../../src/domain/Toy";
import {ToyBuilder} from "../ToyBuilder";
import {DeliverToy} from "../../src/usecases/DeliverToy";
import {StockUnit} from "../../src/domain/StockUnit";
import {Time} from "../Time";
import {StockReducedEvent} from "../../src/domain/StockReducedEvent";
import * as E from "fp-ts/Either";
import {leftValue, rightValue} from "../fp.extensions";
import {Unit} from "../../src/domain/core/Unit";
import {DomainError} from "../../src/domain/core/DomainError";
import {IEvent} from "../../src/domain/core/IEvent";

describe('ToyDeliveryTests', () => {
    let toyRepository: InMemoryToyRepository;
    let useCase: ToyDeliveryUseCase;

    beforeEach(() => {
        toyRepository = new InMemoryToyRepository();
        useCase = new ToyDeliveryUseCase(toyRepository);
    });

    const forASuppliedToy = (stock: number = 1): Toy => {
        const toy = ToyBuilder.toysInStock(stock).build();
        toyRepository.save(toy);
        return toy;
    };

    describe('Successfully When', () => {
        test('toy and update stock', () => {
            const toy = forASuppliedToy(1);
            const command = new DeliverToy(faker.commerce.product(), toy.name);
            const result = useCase.handle(command);

            expect(E.isRight(result)).toBeTruthy();
            expect(rightValue(result)).toBe(Unit.default);
            expect(toy.version).toBe(2);

            assertHaveRaisedEvent(
                toyRepository,
                new StockReducedEvent(
                    toy.getId(),
                    Time.now,
                    command.desiredToy,
                    new StockUnit(0)
                )
            );
        });
    });

    const assertHaveRaisedEvent = <TEvent extends IEvent>(
        repository: InMemoryToyRepository,
        expectedEvent: TEvent
    ) => {
        const raisedEvents = repository.raisedEvents();
        const lastEvent = raisedEvents[raisedEvents.length - 1];
        expect(lastEvent).toStrictEqual(expectedEvent);
    };

    describe('Fail When', () => {
        test('toy has not been built', () => {
            const notBuiltToy = 'Not a Bike';
            const result = useCase.handle(
                new DeliverToy(faker.commerce.product(), notBuiltToy)
            );

            expect(E.isLeft(result)).toBeTruthy();
            expect(leftValue(result)).toStrictEqual(DomainError.anError(`Oops we have a problem... we have not built the toy: ${notBuiltToy}`));
            assertThatNoEventHasBeenRaised();
        });

        test('toy is not supplied anymore', () => {
            const toy = forASuppliedToy(0);
            const result = useCase.handle(
                new DeliverToy(faker.commerce.product(), toy.name)
            );

            expect(E.isLeft(result)).toBeTruthy();
            expect(leftValue(result)).toStrictEqual(DomainError.anError(`No more ${toy.name} in stock`));
            assertThatNoEventHasBeenRaised();
        });

        const assertThatNoEventHasBeenRaised = () => {
            expect(toyRepository.raisedEvents()).toHaveLength(0);
        };
    });
});