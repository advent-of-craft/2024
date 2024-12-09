package gift;

public class Child {
    private final String name;
    private final Behavior behavior;
    private final GiftRequest giftRequest;

    public Child(String firstName, String lastName, int age, Behavior behavior, GiftRequest giftRequest) {
        this.name = firstName + " " + lastName;
        this.behavior = behavior;
        this.giftRequest = giftRequest;
    }

    public String getName() {
        return name;
    }

    public Behavior getBehavior() {
        return behavior;
    }

    public GiftRequest getGiftRequest() {
        return giftRequest;
    }
}