package preparation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SantaWorkshopServiceTest {
    private static final String RECOMMENDED_AGE = "recommendedAge";
    private SantaWorkshopService service;

    @BeforeEach
    void setUp() {
        service = new SantaWorkshopService();
    }

    @Test
    void prepareGiftWithValidToyShouldInstantiateIt() {
        var giftName = "Bitzee";
        double weight = 3;
        var color = "Purple";
        var material = "Plastic";

        var gift = service.prepareGift(giftName, weight, color, material);

        assertThat(gift).isNotNull();
    }

    @Test
    void retrieveAttributeOnGift() {
        var giftName = "Furby";
        double weight = 1;
        var color = "Multi";
        var material = "Cotton";

        var gift = service.prepareGift(giftName, weight, color, material);
        gift.addAttribute(RECOMMENDED_AGE, "3");

        assertThat(gift.getRecommendedAge())
                .isEqualTo(3);
    }

    @Test
    void failsForATooHeavyGift() {
        var giftName = "Dog-E";
        double weight = 6;
        var color = "White";
        var material = "Metal";

        assertThatThrownBy(() -> service.prepareGift(giftName, weight, color, material))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Gift is too heavy for Santa's sleigh");
    }
}
