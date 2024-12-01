import communication.SantaCommunicator;
import doubles.TestLogger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SantaCommunicatorTest {
    private static final String DASHER = "Dasher";
    private static final String NORTH_POLE = "North Pole";
    private final int numberOfDaysToRest = 2;
    private final int numberOfDayBeforeChristmas = 24;
    private final TestLogger logger = new TestLogger();
    private SantaCommunicator communicator;

    @BeforeEach
    void setup() {
        this.communicator = new SantaCommunicator(numberOfDaysToRest);
    }

    @Test
    void composeMessage() {
        var message = communicator.composeMessage(DASHER, NORTH_POLE, 5, numberOfDayBeforeChristmas);
        assertThat(message).isEqualTo("Dear Dasher, please return from North Pole in 17 day(s) to be ready and rest before Christmas.");
    }

    @Test
    void shouldDetectOverdueReindeer() {
        var overdue = communicator.isOverdue(
                DASHER,
                NORTH_POLE,
                numberOfDayBeforeChristmas,
                numberOfDayBeforeChristmas,
                logger);

        assertThat(overdue).isTrue();
        assertThat(logger.getLog())
                .isEqualTo("Overdue for Dasher located North Pole.");
    }

    @Test
    void shouldReturnFalseWhenNoOverdue() {
        assertThat(
                communicator.isOverdue(
                        DASHER,
                        NORTH_POLE,
                        numberOfDayBeforeChristmas - numberOfDaysToRest - 1,
                        numberOfDayBeforeChristmas,
                        logger)
        ).isFalse();
    }
}