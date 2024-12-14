package santamarket.model;

import com.google.common.math.DoubleMath;
import java.util.Objects;

public class ReceiptItem {
    private final Product product;
    private final double price;
    private final double totalPrice;
    private final double quantity;

    ReceiptItem(Product p, double quantity, double price, double totalPrice) {
        this.product = p;
        this.quantity = quantity;
        this.price = price;
        this.totalPrice = totalPrice;
    }

    public double getPrice() {
        return price;
    }

    public Product getProduct() {
        return product;
    }

    public double getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReceiptItem)) return false;
        ReceiptItem that = (ReceiptItem) o;
        double tolerance = 0.001;
        return DoubleMath.fuzzyCompare(that.price, price, tolerance) == 0 &&
                DoubleMath.fuzzyCompare(that.totalPrice, totalPrice, tolerance) == 0 &&
                DoubleMath.fuzzyCompare(that.quantity, quantity, tolerance) == 0 &&
                Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, price, totalPrice, quantity);
    }

    @Override
    public String toString() {
        return "ReceiptItem{" +
                "product=" + product +
                ", price=" + price +
                ", totalPrice=" + totalPrice +
                ", quantity=" + quantity +
                '}';
    }
}
