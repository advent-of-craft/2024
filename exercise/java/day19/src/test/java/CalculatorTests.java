import static org.assertj.core.api.Assertions.*;
import static travel.SantaTravelCalculator.calculateTotalDistanceRecursively;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class CalculatorTests {

    @ParameterizedTest
    @CsvSource({
            "1, 1",
            "2, 3",
            "5, 31",
            "10, 1023",
            "20, 1048575",
            "30, 1073741823"
    })
    void shouldCalculateTheDistanceFor(int numberOfReindeers, int expectedDistance) {
        assertThat(calculateTotalDistanceRecursively(numberOfReindeers)).isEqualTo(expectedDistance);
    }

    @ParameterizedTest
    @ValueSource(ints = {32, 50})
    void failForNumbersGreaterThan32(int numberOfReindeers) {
        assertThatThrownBy(() -> calculateTotalDistanceRecursively(numberOfReindeers))
                .isInstanceOf(ArithmeticException.class)
                .hasMessage("Integer overflow");
    }
}
