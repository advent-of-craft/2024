package reindeer.model;

import java.util.UUID;

public record Reindeer(UUID id, String name, ReindeerColor color) {
}
