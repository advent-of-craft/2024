package core.control.data;

import external.deer.Reindeer;
import io.vavr.collection.List;
import io.vavr.collection.Seq;

public class Reindeers {
    private final Reindeer dasher = new Reindeer("Dasher", 4, 10);
    private final Reindeer dancer = new Reindeer("Dancer", 2, 8);
    private final Reindeer prancer = new Reindeer("Prancer", 3, 9);
    private final Reindeer vixen = new Reindeer("Vixen", 3, 6);
    private final Reindeer comet = new Reindeer("Comet", 4, 9);
    private final Reindeer cupid = new Reindeer("Cupid", 4, 6);
    private final Reindeer donner = new Reindeer("Donner", 7, 6);
    private final Reindeer blitzen = new Reindeer("Blitzen", 8, 7);
    private final Reindeer rudolph = new Reindeer("Rudolph", 6, 3);

    public Seq<Reindeer> getAllHealthyReindeer() {
        return List.of(
                dasher,
                dancer,
                prancer,
                vixen,
                comet,
                cupid,
                donner,
                blitzen,
                rudolph
        );
    }

    public Seq<Reindeer> getAllReindeerWithSickOnes() {
        dasher.sick = true;
        comet.sick = true;

        return getAllHealthyReindeer();
    }
}
