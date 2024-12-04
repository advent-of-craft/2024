package preparation;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.lang.Integer.MAX_VALUE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SantaWorkshopServiceTest {
    private static final String RECOMMENDED_AGE = "recommendedAge";
    private final Faker faker = new Faker();
    private SantaWorkshopService service;

    @BeforeEach
    void setUp() {
        service = new SantaWorkshopService();
    }

    @Test
    void prepareGiftWithValidToyShouldInstantiateIt() {
        assertThat(prepareGiftFor(aValidWeight())).isNotNull();
    }

    @Test
    void retrieveAttributeOnGift() {
        var gift = prepareGiftFor(aValidWeight());
        gift.addAttribute(RECOMMENDED_AGE, "3");

        assertThat(gift.getRecommendedAge())
                .isEqualTo(3);
    }

    private Gift prepareGiftFor(double weight) {
        return service.prepareGift(
                faker.commerce().productName(),
                weight,
                faker.color().name(),
                faker.options().option("Plastic", "Wood", "Metal"));
    }

    private double aValidWeight() {
        return faker.number().randomDouble(3, 0, 5);
    }

    @Test
    void failsForATooHeavyGift() {
        var invalidWeight = faker.number().randomDouble(3, 6, MAX_VALUE);
        assertThatThrownBy(() -> prepareGiftFor(invalidWeight))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Gift is too heavy for Santa's sleigh");
    }
}