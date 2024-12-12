package gifts;

import java.util.List;

public class Child {

    private final String name;
    private final String behavior;
    private List<Toy> wishlist;

    public Child(String name, String behavior) {
        this.name = name;
        this.behavior = behavior;
    }

    public String getBehavior() {
        return behavior;
    }

    public List<Toy> getWishlist() {
        return wishlist;
    }

    public String getName() {
        return name;
    }

    public void setWishList(Toy firstChoice, Toy secondChoice, Toy thirdChoice) {
        this.wishlist = List.of(firstChoice, secondChoice, thirdChoice);
    }
}
