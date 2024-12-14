package santamarket.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static santamarket.model.ProductUnit.EACH;

class ComputeDiscountTest {
    public static final String THREE_FOR_ONE = "3 for 1";
    public static final Product TEDDY_BEAR = new Product("teddyBear", EACH);

    static Stream<Arguments> provideXForYOfferTestCases() {
        return Stream.of(
                Arguments.of(new XForYOffer(1, 1), 1, 1, null),
                Arguments.of(new XForYOffer(3, 1), 3, 1, new Discount(TEDDY_BEAR, THREE_FOR_ONE, -2)),
                Arguments.of(new XForYOffer(3, 1), 4, 1, new Discount(TEDDY_BEAR, THREE_FOR_ONE, -2)),
                Arguments.of(new XForYOffer(3, 1), 8, 1, new Discount(TEDDY_BEAR, THREE_FOR_ONE, -4))
        );
    }

    @ParameterizedTest
    @MethodSource("provideXForYOfferTestCases")
    void computeXForYDiscount(XForYOffer offer, int quantity, double price, Discount expectedDiscount) {
        var shoppingSleigh = new ShoppingSleigh();
        var discount = shoppingSleigh.computeXForYDiscount(offer, TEDDY_BEAR, quantity, price);

        assertEquals(expectedDiscount, discount);
    }
}