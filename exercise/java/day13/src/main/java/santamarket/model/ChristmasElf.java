package santamarket.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChristmasElf {
    private final SantamarketCatalog catalog;
    private final Map<Product, Offer> offers = new HashMap<>();

    public ChristmasElf(SantamarketCatalog catalog) {
        this.catalog = catalog;
    }

    public void addSpecialOffer(SpecialOfferType offerType, Product product, double argument) {
        offers.put(product, new Offer(offerType, product, argument));
    }

    public Receipt checksOutArticlesFrom(ShoppingSleigh thesleigh) {
        Receipt receipt = new Receipt();
        List<ProductQuantity> productQuantities = thesleigh.getItems();
        for (ProductQuantity pq : productQuantities) {
            Product p = pq.getProduct();
            double quantity = pq.getQuantity();
            double unitPrice = catalog.getUnitPrice(p);
            double price = quantity * unitPrice;
            receipt.addProduct(p, quantity, unitPrice, price);
        }
        thesleigh.handleOffers(receipt, offers, catalog);

        return receipt;
    }
}
