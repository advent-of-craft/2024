import {Event} from "./core/Event"
import {StockUnit} from "./StockUnit";

export class StockReducedEvent extends Event {
    constructor(
        id: string,
        date: Date,
        public toyName: string,
        public newStock: StockUnit
    ) {
        super(id, 1, date);
    }
}
