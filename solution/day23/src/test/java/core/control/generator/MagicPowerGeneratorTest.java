package core.control.generator;

import core.control.PowerGenerator;
import external.deer.Reindeer;
import io.vavr.collection.List;
import org.junit.jupiter.api.Test;

import static core.control.generator.MagicPowerAmplifier.blessedAmplifier;
import static core.control.generator.MagicPowerAmplifier.divineAmplifier;
import static org.assertj.core.api.Assertions.assertThat;

class MagicPowerGeneratorTest {
    @Test
    void tellIfItHasEnoughPowerToReachNeededPower() {
        assertThat(getConcentrator(youngReindeerWithSpirit(5)).hasEnoughPowerToReach(1).getAsBoolean())
                .isTrue();
    }

    @Test
    void harnessAllPowerSuccessfully() {
        assertThat(getConcentrator(youngReindeerWithSpirit(5)).harnessAllPower())
                .isEqualTo(5);
    }

    @Test
    void harnessAllPowerSuccessfullyWithBlessedAmplifier() {
        assertThat(getConcentrator(youngReindeerWithSpirit(5), blessedAmplifier()).harnessAllPower())
                .isEqualTo(10);
    }

    @Test
    void harnessAllPowerSuccessfullyWithDivineAmplifier() {
        assertThat(getConcentrator(youngReindeerWithSpirit(5), divineAmplifier()).harnessAllPower())
                .isEqualTo(15);
    }

    @Test
    void harnessAllPowerOfFatiguedIsZero() {
        assertThat(getConcentrator(fatiguedReindeerWithSpirit(5)).harnessAllPower())
                .isZero();
    }

    @Test
    void rechargeSuccessfully() {
        var originalSpiritPower = 10;
        var concentrator = getConcentrator(fatiguedReindeerWithSpirit(originalSpiritPower));

        concentrator.rechargePower();

        assertThat(concentrator.harnessAllPower()).isEqualTo(originalSpiritPower);
    }

    private MagicPowerGenerator getConcentrator(Reindeer reindeer) {
        return new MagicPowerGenerator(
                List.of(new ReindeerPowerUnit(reindeer))
        );
    }


    private PowerGenerator getConcentrator(Reindeer reindeer, MagicPowerAmplifier magicPowerAmplifier) {
        return new MagicPowerGenerator(
                List.of(new ReindeerPowerUnit(reindeer, magicPowerAmplifier))
        );
    }

    private Reindeer fatiguedReindeerWithSpirit(int spirit) {
        var fatiguedReindeer = youngReindeerWithSpirit(spirit);
        fatiguedReindeer.timesHarnessing = 100;
        return fatiguedReindeer;
    }

    private Reindeer youngReindeerWithSpirit(int spirit) {
        return new Reindeer("dede", 2, spirit);
    }
}
