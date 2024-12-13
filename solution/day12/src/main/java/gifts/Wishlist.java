package gifts;

import org.javatuples.Triplet;

class Wishlist {
    private final Triplet<Toy, Toy, Toy> wishes;

    public Wishlist(Toy[] toys) {
        wishes = Triplet.fromArray(toys);
    }

    public Toy getFirstChoice() {
        return wishes.getValue0();
    }

    public Toy getSecondChoice() {
        return wishes.getValue1();
    }

    public Toy getThirdChoice() {
        return wishes.getValue2();
    }
}