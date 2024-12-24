package delivery.unit;

import delivery.Time;
import domain.Toy;
import io.vavr.test.Arbitrary;
import io.vavr.test.Gen;
import org.junit.jupiter.api.Test;

import static io.vavr.test.Property.def;

class CreateToyTests {
    private static final int MAX_TOYS = 1_000_000_000;
    private static final Arbitrary<Integer> INVALID_STOCK = Gen.choose(1, MAX_TOYS).map(x -> -x).arbitrary();
    private static final Arbitrary<Integer> VALID_STOCK = Gen.choose(0, MAX_TOYS).arbitrary();

    @Test
    void canNotCreateToyWithInvalidStock() {
        def("can not create toy with invalid stock (<0)")
                .forAll(INVALID_STOCK)
                .suchThat(stock -> Toy.create(Time.PROVIDER, "", stock).isLeft())
                .check()
                .assertIsSatisfied();
    }

    @Test
    void canCreateToyWithValidStock() {
        def("can create toy with valid stock (>= 0)")
                .forAll(VALID_STOCK)
                .suchThat(stock -> Toy.create(Time.PROVIDER, "", stock).isRight())
                .check()
                .assertIsSatisfied();
    }
}