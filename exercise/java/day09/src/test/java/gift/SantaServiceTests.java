package gift;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class SantaServiceTests {

    private final SantaService service = new SantaService();

    @Test
    void requestIsApprovedForNiceChildWithFeasibleGift() {
        Child niceChild = new Child("Alice", "Thomas", 9, Behavior.NICE, new GiftRequest("Bicycle", true, Priority.NICE_TO_HAVE));
        assertThat(service.evaluateRequest(niceChild)).isTrue();
    }

    @Test
    void requestIsDeniedForNaughtyChild() {
        Child naughtyChild = new Child("Noa", "Thierry", 6, Behavior.NAUGHTY, new GiftRequest("SomeToy", true, Priority.DREAM));
        assertThat(service.evaluateRequest(naughtyChild)).isFalse();
    }

    @Test
    void requestIsDeniedForNiceChildWithInfeasibleGift() {
        Child niceChildWithInfeasibleGift = new Child("Charlie", "Joie", 3, Behavior.NICE, new GiftRequest("AnotherToy", false, Priority.DREAM));
        assertThat(service.evaluateRequest(niceChildWithInfeasibleGift)).isFalse();
    }
}