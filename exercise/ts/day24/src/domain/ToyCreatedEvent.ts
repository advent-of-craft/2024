import {Event} from "./core/Event"
import {StockUnit} from "./StockUnit";

export class ToyCreatedEvent extends Event {
    constructor(
        id: string,
        date: Date,
        public name: string,
        public stock: StockUnit
    ) {
        super(id, 1, date);
    }
}
