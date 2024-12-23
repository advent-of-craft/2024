package core.control;

import external.deer.Reindeer;

public class ReindeerPowerUnit {
    public Reindeer reindeer;
    public MagicPowerAmplifier amplifier = new MagicPowerAmplifier(AmplifierType.BASIC);

    public ReindeerPowerUnit(Reindeer reindeer) {
        this.reindeer = reindeer;
    }

    public float harnessMagicPower() {
        if (!reindeer.needsRest()) {
            reindeer.timesHarnessing++;
            return amplifier.amplify(reindeer.getMagicPower());
        }

        return 0;
    }

    public float checkMagicPower() {
        return reindeer.getMagicPower();
    }
}
