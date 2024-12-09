package domain;

public class Toy {
    public enum State {
        UNASSIGNED, IN_PRODUCTION, COMPLETED
    }

    private final String name;
    private State state;

    public Toy(String name) {
        this.name = name;
        this.state = State.UNASSIGNED;
    }

    public String getName() {
        return name;
    }

    public State getState() {
        return state;
    }

    public void assignToElf() {
        if(state == State.UNASSIGNED)
            this.state = State.IN_PRODUCTION;
    }
}