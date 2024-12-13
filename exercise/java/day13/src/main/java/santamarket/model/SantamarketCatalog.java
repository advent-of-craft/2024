package santamarket.model;

public interface SantamarketCatalog {
    void addProduct(Product product, double price);

    double getUnitPrice(Product product);
}
