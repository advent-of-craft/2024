package santaChristmasList.operations;

import santaChristmasList.operations.dependencies.Factory;
import santaChristmasList.operations.dependencies.Inventory;
import santaChristmasList.operations.dependencies.WishList;
import santaChristmasList.operations.models.Child;
import santaChristmasList.operations.models.Gift;
import santaChristmasList.operations.models.Sleigh;

public class Business {

    private final Factory factory;
    private final Inventory inventory;
    private final WishList wishList;

    public Business(Factory factory, Inventory inventory, WishList wishList) {
        this.factory = factory;
        this.inventory = inventory;
        this.wishList = wishList;
    }

    public Sleigh loadGiftsInSleigh(Child... children) {
        Sleigh sleigh = new Sleigh();

        for (Child child : children) {
            Gift gift = wishList.identifyGift(child);
            if (gift != null) {
                var manufacturedGift = factory.findManufacturedGift(gift);
                if (manufacturedGift != null) {
                    Gift finalGift = inventory.pickUpGift(manufacturedGift.barCode());
                    if (finalGift != null) {
                        sleigh.put(child, "Gift: " + finalGift.name() + " has been loaded!");
                    }
                }
            }
        }
        return sleigh;
    }
}