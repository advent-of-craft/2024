package gifts;

import io.vavr.test.Arbitrary;
import io.vavr.test.Gen;
import io.vavr.test.Property;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;

class CreateChildPropertyTest {
    private final Faker faker = new Faker();

    @Test
    void can_not_create_a_child_with_invalid_number_of_toys() {
        var toyGen = Gen.of(faker.name().name()).map(Toy::new).arbitrary();
        var invalidToys = Arbitrary.list(toyGen).filter(list -> list.size() != 3);

        Property.def("Child cannot be created with toy wishes != 3")
                .forAll(invalidToys)
                .suchThat(t -> Child.create(faker.name().firstName(), Behavior.NAUGHTY, t.toJavaArray(Toy[]::new)).isEmpty())
                .check()
                .assertIsSatisfied();
    }
}