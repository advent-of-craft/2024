import {Either, left, right} from 'fp-ts/lib/Either';
import {EventSourcedAggregate} from "./core/EventSourcedAggregate";
import {StockUnit} from "./StockUnit";
import {ToyCreatedEvent} from "./ToyCreatedEvent";
import {StockReducedEvent} from "./StockReducedEvent";
import {pipe} from 'fp-ts/function';
import * as E from "fp-ts/Either";
import {DomainError} from "./core/DomainError";

export class Toy extends EventSourcedAggregate {
    name?: string;
    private stock: StockUnit;

    private constructor(timeProvider: () => Date, name: string, stock: StockUnit) {
        super(timeProvider);
        this.raiseEvent(new ToyCreatedEvent(this.id, this.time(), name, stock));
    }

    static create(timeProvider: () => Date, name: string, stock: number): Either<DomainError, Toy> {
        return pipe(
            StockUnit.from(stock),
            E.map(s => new Toy(timeProvider, name, s))
        );
    }

    private applyToyCreated(toy: Toy, event: ToyCreatedEvent): void {
        toy.id = event.id;
        toy.name = event.name;
        toy.stock = event.stock;
    }

    reduceStock(): Either<DomainError, Toy> {
        if (!this.stock.isSupplied()) return left(new DomainError(`No more ${this.name} in stock`));
        this.raiseEvent(new StockReducedEvent(this.id, this.time(), this.name!, this.stock.decrease()));

        return right(this);
    }

    private applyStockReduced(toy: Toy, event: StockReducedEvent): void {
        toy.stock = event.newStock;
    }

    protected registerRoutes(): void {
        this.registerEventRoute(ToyCreatedEvent, evt => this.applyToyCreated(this, evt));
        this.registerEventRoute(StockReducedEvent, evt => this.applyStockReduced(this, evt));
    }
}