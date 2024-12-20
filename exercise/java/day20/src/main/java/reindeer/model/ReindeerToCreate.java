package reindeer.model;

import java.util.UUID;

public record ReindeerToCreate(UUID id, String name, ReindeerColor color) {
}
