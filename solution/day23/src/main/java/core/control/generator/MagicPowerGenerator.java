package core.control.generator;

import core.control.PowerGenerator;
import io.vavr.collection.Seq;

import java.util.function.BooleanSupplier;

public class MagicPowerGenerator implements PowerGenerator {
    private final Seq<ReindeerPowerUnit> reindeerPowerUnits;

    public MagicPowerGenerator(Seq<ReindeerPowerUnit> reindeerPowerUnits) {
        this.reindeerPowerUnits = reindeerPowerUnits;
    }

    @Override
    public BooleanSupplier hasEnoughPowerToReach(int powerNeeded) {
        return () -> reindeerPowerUnits
                .map(r -> r.checkMagicPower().getOrElse(0f))
                .sum()
                .intValue() >= powerNeeded;
    }

    @Override
    public int harnessAllPower() {
        return reindeerPowerUnits
                .map(reindeerPowerUnit -> reindeerPowerUnit.harnessMagicPower().getOrElse(0f))
                .sum()
                .intValue();
    }

    @Override
    public void rechargePower() {
        // The reindeer rests so the times to harness his magic power resets
        reindeerPowerUnits.forEach(ReindeerPowerUnit::recharge);
    }
}
