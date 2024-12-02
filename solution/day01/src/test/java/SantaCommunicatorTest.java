import communication.Configuration;
import communication.Reindeer;
import communication.SantaCommunicator;
import doubles.TestLogger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SantaCommunicatorTest {
    private final int numberOfDaysToRest = 2;
    private final int numberOfDayBeforeChristmas = 24;
    private final TestLogger logger = new TestLogger();
    private SantaCommunicator communicator;

    @BeforeEach
    void setup() {
        this.communicator = new SantaCommunicator(new Configuration(numberOfDaysToRest, numberOfDayBeforeChristmas));
    }

    @Test
    void composeMessage() {
        assertThat(communicator.composeMessage(
                reindeer(5)
        )).isEqualTo("Dear Dasher, please return from North Pole in 17 day(s) to be ready and rest before Christmas.");
    }

    @Test
    void shouldDetectOverdueReindeer() {
        var overdue = communicator.isOverdue(
                reindeer(numberOfDayBeforeChristmas),
                logger);

        assertThat(overdue).isTrue();
        assertThat(logger.getLog())
                .isEqualTo("Overdue for Dasher located North Pole.");
    }

    @Test
    void shouldReturnFalseWhenNoOverdue() {
        assertThat(communicator.isOverdue(
                reindeer(numberOfDayBeforeChristmas - numberOfDaysToRest - 1),
                logger)
        ).isFalse();
    }

    private static Reindeer reindeer(int numbersOfDaysForComingBack) {
        return new Reindeer("Dasher", "North Pole", numbersOfDaysForComingBack);
    }
}