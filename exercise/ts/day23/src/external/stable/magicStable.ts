import {Reindeer} from "../deer/reindeer";

export class MagicStable {
    // Dasher is sick since yesterday
    private dasher = new Reindeer("Dasher", 4, 10, true);
    private dancer = new Reindeer("Dancer", 2, 8);
    private prancer = new Reindeer("Prancer", 3, 9);
    private vixen = new Reindeer("Vixen", 3, 6);
    // Comet is sick as well
    private comet = new Reindeer("Comet", 4, 9, true);
    private cupid = new Reindeer("Cupid", 4, 6);
    private donner = new Reindeer("Donner", 7, 6);
    private blitzen = new Reindeer("Blitzen", 8, 7);
    private rudolph = new Reindeer("Rudolph", 6, 3);

    public getAllReindeers(): Reindeer[] {
        return [
            this.dasher, this.dancer, this.prancer, this.vixen,
            this.comet, this.cupid, this.donner, this.blitzen, this.rudolph
        ];
    }
}
