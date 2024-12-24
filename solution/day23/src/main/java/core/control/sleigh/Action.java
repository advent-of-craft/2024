package core.control.sleigh;

public enum Action {
    FLYING("Flying"),
    HOVERING("Hovering"),
    PARKED("Parked");

    private final String description;

    Action(String description) {

        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
