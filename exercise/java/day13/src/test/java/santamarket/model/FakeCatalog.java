package santamarket.model;

import java.util.HashMap;
import java.util.Map;

public class FakeCatalog implements SantamarketCatalog {
    private final Map<String, Product> products = new HashMap<>();
    private final Map<String, Double> prices = new HashMap<>();

    @Override
    public void addProduct(Product product, double price) {
        this.products.put(product.getName(), product);
        this.prices.put(product.getName(), price);
    }

    @Override
    public double getUnitPrice(Product p) {
        return this.prices.get(p.getName());
    }
}
