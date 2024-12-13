package gifts;

import java.util.Optional;

public class Child {
    private final String name;
    private final Behavior behavior;
    private final Wishlist wishlist;

    private Child(String name, Behavior behavior, Wishlist wishlist) {
        this.name = name;
        this.behavior = behavior;
        this.wishlist = wishlist;
    }

    public static Optional<Child> create(String name, Behavior behavior, Toy... toys) {
        if (toys == null || toys.length != 3) {
            return Optional.empty();
        }
        return Optional.of(new Child(name, behavior, new Wishlist(toys)));
    }

    public String getName() {
        return name;
    }

    public Toy chooseToy() {
        return switch (behavior) {
            case Behavior.NAUGHTY -> wishlist.getThirdChoice();
            case Behavior.NICE -> wishlist.getSecondChoice();
            case Behavior.VERY_NICE -> wishlist.getFirstChoice();
        };
    }
}
