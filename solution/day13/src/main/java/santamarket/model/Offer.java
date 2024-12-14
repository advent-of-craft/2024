package santamarket.model;

public class Offer {
    SpecialOfferType offerType;
    double argument;

    public Offer(SpecialOfferType offerType, double argument) {
        this.offerType = offerType;
        this.argument = argument;
    }
}
