package gift;

public class GiftRequest {
    private final String giftName;
    private final boolean isFeasible;
    private final Priority priority;

    public GiftRequest(String giftName, boolean isFeasible, Priority priority) {
        this.giftName = giftName;
        this.isFeasible = isFeasible;
        this.priority = priority;
    }

    public String getGiftName() {
        return giftName;
    }

    public boolean isFeasible() {
        return isFeasible;
    }

    public Priority getPriority() {
        return priority;
    }
}