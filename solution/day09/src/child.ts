import {GiftRequest} from "./giftRequest";

export class Child {
    public readonly name: string;
    public readonly behavior: Behavior;
    public readonly giftRequest: GiftRequest;

    constructor(firstName: string,
                lastName: string,
                age: number,
                behavior: Behavior,
                giftRequest: GiftRequest) {
        this.name = firstName;
        this.behavior = behavior;
        this.giftRequest = giftRequest;
    }
}