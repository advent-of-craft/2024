package external.stable;

import external.deer.Reindeer;

import java.util.ArrayList;

public class MagicStable {
    //Dasher is sick since yesterday
    Reindeer dasher = new Reindeer("Dasher", 4, 10, true);
    Reindeer dancer = new Reindeer("Dancer", 2, 8);
    Reindeer prancer = new Reindeer("Prancer", 3, 9);
    Reindeer vixen = new Reindeer("Vixen", 3, 6);
    // Comet is sick as well
    Reindeer comet = new Reindeer("Comet", 4, 9, true);
    Reindeer cupid = new Reindeer("Cupid", 4, 6);
    Reindeer donner = new Reindeer("Donner", 7, 6);
    Reindeer blitzen = new Reindeer("Blitzen", 8, 7);
    Reindeer rudolph = new Reindeer("Rudolph", 6, 3);

    public ArrayList<Reindeer> getAllReindeers() {
        var allReindeers = new ArrayList<Reindeer>();

        allReindeers.add(dasher);
        allReindeers.add(dancer);
        allReindeers.add(prancer);
        allReindeers.add(vixen);
        allReindeers.add(comet);
        allReindeers.add(cupid);
        allReindeers.add(donner);
        allReindeers.add(blitzen);
        allReindeers.add(rudolph);

        return allReindeers;
    }
}
