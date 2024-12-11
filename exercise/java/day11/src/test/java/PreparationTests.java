import christmas.Preparation;
import christmas.ToyType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class PreparationTests {

    @ParameterizedTest
    @CsvSource({
            "-1, No gifts to prepare.",
            "0, No gifts to prepare.",
            "1, Elves will prepare the gifts.",
            "49, Elves will prepare the gifts.",
            "50, Santa will prepare the gifts."
    })
    void prepareGifts(int numberOfGifts, String expected) {
        String result = Preparation.prepareGifts(numberOfGifts);
        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "1, Baby",
            "3, Toddler",
            "6, Child",
            "13, Teen"
    })
    void categorizeGift(int age, String expectedCategory) {
        String result = Preparation.categorizeGift(age);
        assertThat(result).isEqualTo(expectedCategory);
    }

    @ParameterizedTest
    @CsvSource({
            "EDUCATIONAL, 25, 100, true",
            "FUN, 30, 100, true",
            "CREATIVE, 20, 100, true",
            "EDUCATIONAL, 20, 100, false",
            "FUN, 29, 100, false",
            "CREATIVE, 15, 100, false"
    })
    void ensureToyBalance(ToyType toyType, int toysCount, int totalToys, boolean expected) {
        boolean result = Preparation.ensureToyBalance(toyType, toysCount, totalToys);
        assertThat(result).isEqualTo(expected);
    }
}
