import {Child, Gift, ManufacturedGift} from './models';

export class Factory extends Map<Gift, ManufacturedGift> {
    findManufacturedGift(gift: Gift): ManufacturedGift | undefined {
        return this.get(gift);
    }
}

export class Inventory extends Map<string, Gift> {
    pickUpGift(barCode: string): Gift | undefined {
        return this.get(barCode);
    }
}

export class WishList extends Map<Child, Gift> {
    identifyGift(child: Child): Gift | undefined {
        return this.get(child);
    }
}