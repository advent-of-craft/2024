import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static delivery.Building.whichFloor;
import static org.assertj.core.api.Assertions.assertThat;

class DeliveryTest {
    @ParameterizedTest
    @CsvSource({
            "1, 0",
            "2, 3",
            "3, -1",
            "4, 53",
            "5, -3",
            "6, 2920"
    })
    void returnFloorNumberBasedOnInstructions(String fileName, int expectedFloor) throws IOException, URISyntaxException {
        var instructions = loadInstructionsFromFile(fileName + ".txt");
        assertThat(whichFloor(instructions))
                .isEqualTo(expectedFloor);
    }

    private static String loadInstructionsFromFile(String input) throws URISyntaxException, IOException {
        return Files.readString(
                Path.of(Objects.requireNonNull(DeliveryTest.class.getClassLoader().getResource(input)).toURI())
        );
    }
}