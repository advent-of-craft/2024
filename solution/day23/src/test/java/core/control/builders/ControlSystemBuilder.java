package core.control.builders;

import core.control.ControlSystem;
import core.control.dashboard.ConsoleDashboard;
import core.control.data.Reindeers;
import core.control.generator.AmplifierType;
import core.control.generator.MagicPowerGenerator;
import core.control.generator.factory.BestMagicalPerformancePowerUnitFactory;
import core.control.sleigh.XmasSleigh;
import external.deer.Reindeer;
import io.vavr.collection.Map;
import io.vavr.collection.Seq;

public class ControlSystemBuilder {

    private final Seq<Reindeer> allReindeers;
    private Map<Integer, AmplifierType> specialAmplifiers;

    private ControlSystemBuilder(Seq<Reindeer> allReindeers) {
        this.allReindeers = allReindeers;
    }

    public static ControlSystemBuilder functionalSystem() {
        return new ControlSystemBuilder(new Reindeers().getAllHealthyReindeer());
    }

    public static ControlSystemBuilder systemWithReindeer(Seq<Reindeer> reindeerList) {
        return new ControlSystemBuilder(reindeerList);
    }

    public ControlSystemBuilder withSpecialAmplifiers(Map<Integer, AmplifierType> specialAmplifiers) {
        this.specialAmplifiers = specialAmplifiers;
        return this;
    }

    public ControlSystem build() {
        return new ControlSystem(
                new MagicPowerGenerator(
                        new BestMagicalPerformancePowerUnitFactory(
                                allReindeers,
                                specialAmplifiers
                        ).bringAllReindeers()),
                XmasSleigh.defaultPosition(),
                new ConsoleDashboard()
        );
    }
}
