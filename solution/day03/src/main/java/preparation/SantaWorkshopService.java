package preparation;

import java.util.ArrayList;
import java.util.List;

public class SantaWorkshopService {
    private final List<Gift> preparedGifts = new ArrayList<>();

    public Gift prepareGift(String giftName, double weight, String color, String material) {
        if (weight > 5) {
            throw new IllegalArgumentException("Gift is too heavy for Santa's sleigh");
        }

        var gift = new Gift(giftName, weight, color, material);
        preparedGifts.add(gift);

        return gift;
    }
}