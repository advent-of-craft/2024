package core.control.generator;

public enum AmplifierType {
    BASIC(1),
    BLESSED(2),
    DIVINE(3);

    private final int multiplier;

    AmplifierType(int multiplier) {
        this.multiplier = multiplier;
    }

    public int getMultiplier() {
        return multiplier;
    }
}
