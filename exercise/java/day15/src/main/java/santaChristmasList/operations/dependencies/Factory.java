package santaChristmasList.operations.dependencies;

import santaChristmasList.operations.models.Gift;
import santaChristmasList.operations.models.ManufacturedGift;

import java.util.HashMap;

public class Factory extends HashMap<Gift, ManufacturedGift> {
    public ManufacturedGift findManufacturedGift(Gift gift) {
        return this.getOrDefault(gift, null);
    }
}