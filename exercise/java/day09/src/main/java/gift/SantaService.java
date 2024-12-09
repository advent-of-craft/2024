package gift;

public class SantaService {
    public boolean evaluateRequest(Child child) {
        return child.getBehavior() == Behavior.NICE && child.getGiftRequest().isFeasible();
    }
}