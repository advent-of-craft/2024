package delivery;

import com.github.javafaker.Faker;
import domain.Toy;

public class ToyBuilder {
    private final int stock;

    private ToyBuilder(int stock) {
        this.stock = stock;
    }

    public static ToyBuilder toysInStock(int stock) {
        return new ToyBuilder(stock);
    }

    public Toy build() {
        return Toy.create(Time.PROVIDER, Faker.instance().lorem().word(), stock)
                .getOrElseThrow(() -> new RuntimeException("Toy creation failed"));
    }
}