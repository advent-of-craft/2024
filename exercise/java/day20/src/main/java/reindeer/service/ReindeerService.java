package reindeer.service;

import io.vavr.collection.Seq;
import io.vavr.collection.Vector;
import io.vavr.control.Either;
import org.springframework.stereotype.Service;
import reindeer.model.Reindeer;
import reindeer.model.ReindeerColor;
import reindeer.model.ReindeerErrorCode;
import reindeer.model.ReindeerToCreate;

import java.util.UUID;

@Service
public class ReindeerService {
    private Seq<Reindeer> reindeer = Vector.of(
            new Reindeer(UUID.fromString("40f9d24d-d3e0-4596-adc5-b4936ff84b19"), "Petar", ReindeerColor.BLACK)
    );

    public Either<ReindeerErrorCode, Reindeer> get(UUID id) {
        return reindeer.find(r -> r.id().equals(id))
                .toEither(ReindeerErrorCode.NOT_FOUND);
    }

    public Either<ReindeerErrorCode, Reindeer> create(ReindeerToCreate reindeerToCreate) {
        return reindeer.find(r -> r.name().equals(reindeerToCreate.name()))
                .toEither(() -> createAndAddReindeer(reindeerToCreate))
                .swap()
                .mapLeft(r -> ReindeerErrorCode.ALREADY_EXIST);
    }

    private Reindeer createAndAddReindeer(ReindeerToCreate reindeerToCreate) {
        Reindeer newReindeer = new Reindeer(UUID.randomUUID(), reindeerToCreate.name(), reindeerToCreate.color());
        reindeer = reindeer.append(newReindeer);

        return newReindeer;
    }
}
