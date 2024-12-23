import {Reindeer} from "../external/deer/reindeer";
import {MagicPowerAmplifier} from "./magicPowerAmplifier";
import {AmplifierType} from "./amplifierType";

export class ReindeerPowerUnit {
    public reindeer: Reindeer;
    public amplifier: MagicPowerAmplifier = new MagicPowerAmplifier(AmplifierType.BASIC);

    constructor(reindeer: Reindeer) {
        this.reindeer = reindeer;
    }

    public harnessMagicPower(): number {
        if (!this.reindeer.needsRest()) {
            this.reindeer.timesHarnessing++;
            return this.amplifier.amplify(this.reindeer.getMagicPower());
        }
        return 0;
    }

    public checkMagicPower(): number {
        return this.reindeer.getMagicPower();
    }
}
