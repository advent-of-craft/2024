import {none, Option, some} from 'fp-ts/Option';
import {IToyRepository} from "../../src/domain/IToyRepository";
import {Toy} from "../../src/domain/Toy";

import {IEvent} from "../../src/domain/core/IEvent";

export class InMemoryToyRepository implements IToyRepository {
    private _toys: Map<string, Toy> = new Map<string, Toy>();
    private _raisedEvents: IEvent[] = [];

    findByName(name: string): Option<Toy> {
        for (let toy of this._toys.values()) {
            if (toy.name === name) {
                return some(toy);
            }
        }
        return none;
    }

    findById(id: string): Option<Toy> {
        const toy = this._toys.get(id);
        return toy ? some(toy) : none;
    }

    save(toy: Toy): void {
        this._raisedEvents = [];
        this._toys = this._toys.set(toy.getId(), toy);

        toy.getUncommittedEvents().forEach((event) => {
            this._raisedEvents.push(event);
        });

        toy.clearUncommittedEvents();
    }

    raisedEvents(): IEvent[] {
        return this._raisedEvents;
    }
}