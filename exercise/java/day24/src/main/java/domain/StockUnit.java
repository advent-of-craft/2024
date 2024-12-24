package domain;

import io.vavr.control.Either;

import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

public record StockUnit(int value) {

    public static Either<Error, StockUnit> from(int stock) {
        return stock >= 0
                ? right(new StockUnit(stock))
                : left(new Error("A stock unit cannot be negative"));
    }

    public boolean isSupplied() {
        return value > 0;
    }

    public StockUnit decrease() {
        return new StockUnit(value - 1);
    }
}