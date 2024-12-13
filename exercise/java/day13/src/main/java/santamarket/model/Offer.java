package santamarket.model;

public class Offer {
    private final Product product;
    SpecialOfferType offerType;
    double argument;

    public Offer(SpecialOfferType offerType, Product product, double argument) {
        this.offerType = offerType;
        this.argument = argument;
        this.product = product;
    }
}
