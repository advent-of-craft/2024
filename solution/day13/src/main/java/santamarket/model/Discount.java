package santamarket.model;

import com.google.common.math.DoubleMath;
import java.util.Objects;

public class Discount {
    private final String description;
    private final double discountAmount;
    private final Product product;

    public Discount(Product product, String description, double discountAmount) {
        this.product = product;
        this.description = description;
        this.discountAmount = discountAmount;
    }

    public String getDescription() {
        return description;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public Product getProduct() {
        return product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Discount discount = (Discount) o;
        return DoubleMath.fuzzyCompare(discount.discountAmount, discountAmount, 0.001) == 0 && Objects.equals(description, discount.description) && Objects.equals(product, discount.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, discountAmount, product);
    }

    @Override
    public String toString() {
        return "Discount{" +
                "description='" + description + '\'' +
                ", discountAmount=" + discountAmount +
                ", product=" + product +
                '}';
    }
}
