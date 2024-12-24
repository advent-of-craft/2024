import {v4 as uuidv4} from 'uuid';
import {IEvent} from "./IEvent";

export class Event implements IEvent {
    constructor(
        public id: string = uuidv4(),
        public version: number = 1,
        public date: Date = new Date()
    ) {
    }
}