package core.control;

import core.control.data.Reindeers;
import core.control.generator.AmplifierType;
import core.control.sleigh.Action;
import core.control.sleigh.ActionSuccess;
import core.control.sleigh.EngineStatus;
import external.deer.Reindeer;
import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import org.assertj.vavr.api.VavrAssertions;
import org.junit.jupiter.api.Test;

import static core.control.builders.ControlSystemBuilder.functionalSystem;
import static core.control.builders.ControlSystemBuilder.systemWithReindeer;
import static org.assertj.core.api.Assertions.assertThat;

class ControlSystemTest {
    String expectedMessageWhenEngineNotStarted = "The sleigh is not started. Please start the sleigh before any other action...";
    String expectedMessageWhenEngineAlreadyStarted = "System already started";
    String expectedMessageWhenReindeerNeedsRest = "The reindeer needs rest. Please park the sleigh...";
    String expectedMessageWhenSystemNotUsable = "System is not usable. It needs more magical power.";

    @Test
    void notBeUsableIfItCannotHoldEnoughMagicPower() {
        List<Reindeer> noReindeers = List.empty();
        VavrAssertions.assertThat(systemWithReindeer(noReindeers)
                .build()
                .startSystem()
        ).hasLeftValueSatisfying(e -> assertThat(e.failingBecause()).isEqualTo(expectedMessageWhenSystemNotUsable));
    }

    @Test
    void successfullyStart() {
        VavrAssertions.assertThat(
                functionalSystem()
                        .withSpecialAmplifiers(getAvailableAmplifiers())
                        .build()
                        .startSystem()
        ).hasRightValueSatisfying(success -> verifyEngineAndSleighStatus(success, EngineStatus.ON, Action.PARKED));
    }

    @Test
    void notStartIfAlreadyStarted() {
        var startedSystem = functionalSystem()
                .withSpecialAmplifiers(getAvailableAmplifiers())
                .build();

        startedSystem.startSystem();

        VavrAssertions.assertThat(startedSystem.startSystem())
                .hasLeftValueSatisfying(e -> assertThat(e.failingBecause()).isEqualTo(expectedMessageWhenEngineAlreadyStarted));
    }

    @Test
    void notAscendIfSystemIsNotOn() {
        var controlSystem = functionalSystem()
                .withSpecialAmplifiers(getAvailableAmplifiers())
                .build();

        VavrAssertions.assertThat(controlSystem.ascend())
                .hasLeftValueSatisfying(e -> assertThat(e.failingBecause()).isEqualTo(expectedMessageWhenEngineNotStarted));
    }

    @Test
    void successfullyAscend() {
        VavrAssertions.assertThat(functionalSystemOn().ascend())
                .hasRightValueSatisfying(success -> verifyEngineAndSleighStatus(success, EngineStatus.ON, Action.FLYING));
    }

    @Test
    void notAscendIfNotEnoughMagicalPowerAndSuggestRestingReindeer() {
        var controlSystem = systemWithReindeer(new Reindeers().getAllReindeerWithSickOnes())
                .withSpecialAmplifiers(getAvailableAmplifiers())
                .build();

        controlSystem.startSystem();
        controlSystem.ascend();
        controlSystem.descend();
        controlSystem.ascend();
        controlSystem.descend();
        controlSystem.ascend();
        controlSystem.descend();

        VavrAssertions.assertThat(controlSystem.ascend())
                .hasLeftValueSatisfying(e -> assertThat(e.failingBecause()).isEqualTo(expectedMessageWhenReindeerNeedsRest));
    }

    @Test
    void successfullyDescend() {
        VavrAssertions.assertThat(functionalSystemFlying().descend())
                .hasRightValueSatisfying(success -> verifyEngineAndSleighStatus(success, EngineStatus.ON, Action.HOVERING));
    }

    @Test
    void notDescendIfSystemIsNotOn() {
        var controlSystem = functionalSystem()
                .withSpecialAmplifiers(getAvailableAmplifiers())
                .build();

        VavrAssertions.assertThat(controlSystem.descend())
                .hasLeftValueSatisfying(e -> assertThat(e.failingBecause()).isEqualTo(expectedMessageWhenEngineNotStarted));
    }

    @Test
    void successfullyPark() {
        VavrAssertions.assertThat(functionalSystemDescending().park())
                .hasRightValueSatisfying(success -> verifyEngineAndSleighStatus(success, EngineStatus.ON, Action.PARKED));
    }

    @Test
    void notParkIfSystemIsNotOn() {
        var controlSystem = functionalSystem()
                .withSpecialAmplifiers(getAvailableAmplifiers())
                .build();

        VavrAssertions.assertThat(controlSystem.park())
                .hasLeftValueSatisfying(e -> assertThat(e.failingBecause()).isEqualTo(expectedMessageWhenEngineNotStarted));
    }

    @Test
    void successfullyStopAfterTurningOn() {
        VavrAssertions.assertThat(functionalSystemOn().stopSystem())
                .hasRightValueSatisfying(success -> verifyEngineAndSleighStatus(success, EngineStatus.OFF, Action.PARKED));
    }

    @Test
    void successfullyStopAfterFlying() {
        VavrAssertions.assertThat(functionalSystemParking().stopSystem())
                .hasRightValueSatisfying(success -> verifyEngineAndSleighStatus(success, EngineStatus.OFF, Action.PARKED));
    }

    private void verifyEngineAndSleighStatus(ActionSuccess success, EngineStatus engineStatus, Action sleighStatus) {
        assertThat(success.getEngineStatus()).isEqualTo(engineStatus);
        assertThat(success.getSleighStatus()).isEqualTo(sleighStatus);
    }

    private ControlSystem functionalSystemOn() {
        var system = functionalSystem()
                .withSpecialAmplifiers(getAvailableAmplifiers())
                .build();

        system.startSystem();

        return system;
    }

    private ControlSystem functionalSystemFlying() {
        var system = functionalSystemOn();

        system.ascend();

        return system;
    }

    private ControlSystem functionalSystemDescending() {
        var system = functionalSystemFlying();

        system.descend();

        return system;
    }

    private ControlSystem functionalSystemParking() {
        var system = functionalSystemDescending();

        system.park();

        return system;
    }

    private Map<Integer, AmplifierType> getAvailableAmplifiers() {
        return HashMap.of(
                1, AmplifierType.DIVINE,
                2, AmplifierType.BLESSED,
                3, AmplifierType.BLESSED);
    }
}