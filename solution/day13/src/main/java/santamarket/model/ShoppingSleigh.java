package santamarket.model;

import java.util.*;

public class ShoppingSleigh {
    private final List<ProductQuantity> items = new ArrayList<>();
    private final Map<Product, Double> productQuantities = new HashMap<>();

    List<ProductQuantity> getItems() {
        return Collections.unmodifiableList(items);
    }

    void addItem(Product product) {
        addItemQuantity(product, 1.0);
    }

    Map<Product, Double> productQuantities() {
        return Collections.unmodifiableMap(productQuantities);
    }

    public void addItemQuantity(Product product, double quantity) {
        items.add(new ProductQuantity(product, quantity));
        if (productQuantities.containsKey(product)) {
            productQuantities.put(product, productQuantities.get(product) + quantity);
        } else {
            productQuantities.put(product, quantity);
        }
    }

    void handleOffers(Receipt receipt, Map<Product, Offer> offers, SantamarketCatalog catalog) {
        for (Product p : productQuantities().keySet()) {
            double quantity = productQuantities.get(p);
            if (offers.containsKey(p)) {
                Offer offer = offers.get(p);
                Discount discount = null;

                if (offer.offerType == SpecialOfferType.TWO_FOR_ONE) {
                    discount = computeXForYDiscount(new XForYOffer(2, 1), p, (int) quantity, catalog.getUnitPrice(p));
                } else if (offer.offerType == SpecialOfferType.THREE_FOR_TWO) {
                    discount = computeXForYDiscount(new XForYOffer(3, 2), p, (int) quantity, catalog.getUnitPrice(p));
                } else {
                    double unitPrice = catalog.getUnitPrice(p);
                    int quantityAsInt = (int) quantity;

                    int x = 1;
                    if (offer.offerType == SpecialOfferType.TWO_FOR_AMOUNT) {
                        x = 2;
                        if (quantityAsInt >= 2) {
                            double total = offer.argument * ((double) quantityAsInt / x) + quantityAsInt % 2 * unitPrice;
                            double discountN = unitPrice * quantity - total;
                            discount = new Discount(p, "2 for " + offer.argument, -discountN);
                        }
                    }
                    if (offer.offerType == SpecialOfferType.FIVE_FOR_AMOUNT) {
                        x = 5;
                    }
                    int numberOfXs = quantityAsInt / x;
                    if (offer.offerType == SpecialOfferType.TEN_PERCENT_DISCOUNT) {
                        discount = new Discount(p, offer.argument + "% off", -quantity * unitPrice * offer.argument / 100.0);
                    }
                    if (offer.offerType == SpecialOfferType.FIVE_FOR_AMOUNT && quantityAsInt >= 5) {
                        double discountTotal = unitPrice * quantity - (offer.argument * numberOfXs + quantityAsInt % 5 * unitPrice);
                        discount = new Discount(p, x + " for " + offer.argument, -discountTotal);
                    }
                }
                if (discount != null)
                    receipt.addDiscount(discount);
            }
        }
    }

    private Discount computeXForYDiscount(XForYOffer offer, Product p, int quantity, double unitPrice) {
        if (quantity <= offer.y())
            return null;
        int numberOfXs = quantity / offer.x();
        double discountAmount = unitPrice * (quantity - numberOfXs * offer.y() - quantity % offer.x());
        return new Discount(p, offer.x() + " for " + offer.y(), -discountAmount);
    }
}