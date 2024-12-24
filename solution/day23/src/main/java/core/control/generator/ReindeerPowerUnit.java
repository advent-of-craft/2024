package core.control.generator;

import external.deer.Reindeer;
import io.vavr.control.Option;

public class ReindeerPowerUnit {
    private final Reindeer reindeer;
    private final MagicPowerAmplifier amplifier;

    public ReindeerPowerUnit(Reindeer reindeer) {
        this(reindeer, MagicPowerAmplifier.basicAmplifier());
    }

    public ReindeerPowerUnit(Reindeer reindeer, MagicPowerAmplifier amplifier) {
        this.reindeer = reindeer;
        this.amplifier = amplifier;
    }

    public Option<Float> harnessMagicPower() {
        return checkMagicPower()
                .peek(p -> reindeer.timesHarnessing++);
    }

    public Option<Float> checkMagicPower() {
        return Option.of(reindeer)
                .filter(this::canHarnessMagicalPower)
                .map(r -> amplifier.amplify(r.getMagicPower()));
    }

    private boolean canHarnessMagicalPower(Reindeer reindeer) {
        return !reindeer.needsRest();
    }

    public void recharge() {
        reindeer.timesHarnessing = 0;
    }
}