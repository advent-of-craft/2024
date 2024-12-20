package reindeer.model;

import java.util.UUID;

public class ReindeerResult {
    private final UUID id;
    private final String name;
    private final ReindeerColor color;

    private ReindeerResult(UUID id, String name, ReindeerColor color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    public static ReindeerResult from(Reindeer reindeer) {
        return new ReindeerResult(reindeer.id(), reindeer.name(), reindeer.color());
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ReindeerColor getColor() {
        return color;
    }
}
