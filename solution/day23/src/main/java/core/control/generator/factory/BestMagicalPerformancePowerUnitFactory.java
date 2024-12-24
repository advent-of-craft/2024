package core.control.generator.factory;

import core.control.generator.AmplifierType;
import core.control.generator.MagicPowerAmplifier;
import core.control.generator.PowerUnitFactory;
import core.control.generator.ReindeerPowerUnit;
import external.deer.Reindeer;
import io.vavr.collection.Map;
import io.vavr.collection.Seq;

import java.util.Comparator;

public class BestMagicalPerformancePowerUnitFactory implements PowerUnitFactory {
    private final Seq<Reindeer> allReindeers;
    private final Map<Integer, AmplifierType> availableAmplifierByMagicalPower;

    public BestMagicalPerformancePowerUnitFactory(Seq<Reindeer> allReindeers, Map<Integer, AmplifierType> availableAmplifierByMagicalPower) {
        this.allReindeers = allReindeers;
        this.availableAmplifierByMagicalPower = availableAmplifierByMagicalPower;
    }

    @Override
    public Seq<ReindeerPowerUnit> bringAllReindeers() {
        return bringAllReindeers(allReindeers);
    }

    private Seq<ReindeerPowerUnit> bringAllReindeers(Seq<Reindeer> allReindeers) {
        var allReindeerByMagicalPower = allReindeers
                .sorted(Comparator.comparingDouble(Reindeer::getMagicPower).reversed());

        return allReindeerByMagicalPower
                .map(reindeer -> attachPowerUnit(reindeer, allReindeerByMagicalPower.indexOf(reindeer) + 1));
    }

    private ReindeerPowerUnit attachPowerUnit(Reindeer reindeer, int indexOfMagicalPower) {
        return generatePowerUnit(reindeer, availableAmplifierByMagicalPower.getOrElse(indexOfMagicalPower, AmplifierType.BASIC));
    }

    public ReindeerPowerUnit generatePowerUnit(Reindeer reindeer, AmplifierType amplifierToAttach) {
        return new ReindeerPowerUnit(reindeer, new MagicPowerAmplifier(amplifierToAttach));
    }
}
