package game;

public enum Choice {
    ROCK("🪨"),
    PAPER("📄"),
    SCISSORS("✂️");

    private final String symbol;

    Choice(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}