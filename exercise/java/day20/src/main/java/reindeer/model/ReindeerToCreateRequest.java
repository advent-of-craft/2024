package reindeer.model;

import java.util.UUID;

public record ReindeerToCreateRequest(String name, ReindeerColor color) {
    public ReindeerToCreate toDomain() {
        return new ReindeerToCreate(UUID.randomUUID(), name, color);
    }
}
