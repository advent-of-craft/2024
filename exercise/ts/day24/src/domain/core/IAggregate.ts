import {IEvent} from "./IEvent";

export interface IAggregate {
    getId(): string;

    applyEvent(event: IEvent): void;

    getUncommittedEvents(): IEvent[];

    clearUncommittedEvents(): void;
}