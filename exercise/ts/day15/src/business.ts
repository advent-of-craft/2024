import {Child, Gift, Sleigh} from './models';
import {Factory, Inventory, WishList} from "./dependencies";

export class Business {
    constructor(
        private factory: Factory,
        private inventory: Inventory,
        private wishList: WishList
    ) {}

    loadGiftsInSleigh(...children: Child[]): Sleigh {
        const sleigh = new Sleigh();
        children.forEach((child) => {
            const gift = this.wishList.identifyGift(child);
            if (gift) {
                const manufacturedGift = this.factory.findManufacturedGift(gift);
                if (manufacturedGift) {
                    const finalGift = this.inventory.pickUpGift(manufacturedGift.barCode);
                    if (finalGift) {
                        sleigh.set(child, `Gift: ${finalGift.name} has been loaded!`);
                    }
                }
            }
        });
        return sleigh;
    }
}