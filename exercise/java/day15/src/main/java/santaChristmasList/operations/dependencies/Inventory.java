package santaChristmasList.operations.dependencies;

import santaChristmasList.operations.models.Gift;

import java.util.HashMap;

public class Inventory extends HashMap<String, Gift> {
    public Gift pickUpGift(String barCode) {
        return this.getOrDefault(barCode, null);
    }
}