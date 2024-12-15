package santaChristmasList.operations.dependencies;

import santaChristmasList.operations.models.Child;
import santaChristmasList.operations.models.Gift;

import java.util.HashMap;

public class WishList extends HashMap<Child, Gift> {
    public Gift identifyGift(Child child) {
        return this.getOrDefault(child, null);
    }
}