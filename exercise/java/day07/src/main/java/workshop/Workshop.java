package workshop;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Workshop {
    private final List<Gift> gifts = new ArrayList<>();

    public void addGift(Gift gift) {
        gifts.add(gift);
    }

    public Optional<Gift> completeGift(String name) {
        return gifts.stream()
                .filter(gift -> gift.name().equals(name))
                .findFirst()
                .map(gift -> gift.withStatus(Status.PRODUCED));
    }
}
