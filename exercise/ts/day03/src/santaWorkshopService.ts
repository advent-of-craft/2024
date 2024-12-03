import { Gift } from './gift';

export class SantaWorkshopService {
    private preparedGifts: Gift[] = [];

    public prepareGift(giftName: string, weight: number, color: string, material: string): Gift {
        if (weight > 5) {
            throw new Error('Gift is too heavy for Santa\'s sleigh');
        }

        const gift = new Gift(giftName, weight, color, material);
        this.preparedGifts.push(gift);

        return gift;
    }
}