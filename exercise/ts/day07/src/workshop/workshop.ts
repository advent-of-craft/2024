import {Gift} from "./gift";
import {Status} from "./status";

export class Workshop {
    private gifts: Gift[] = [];

    addGift(gift: Gift): void {
        this.gifts.push(gift);
    }

    completeGift(name: string): Gift | null {
        const gift = this.gifts.find(gift => gift.getName() === name);
        return gift ? gift.withStatus(Status.Produced) : null;
    }
}