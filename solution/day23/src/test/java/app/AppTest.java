package app;

import core.control.ControlSystem;
import core.control.dashboard.ConsoleDashboard;
import core.control.generator.MagicPowerGenerator;
import core.control.generator.factory.BestMagicalPerformancePowerUnitFactory;
import core.control.sleigh.XmasSleigh;
import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import org.assertj.vavr.api.VavrAssertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AppTest {
    @Test
    void startSuccessfully() {
        var app = new App();
        VavrAssertions.assertThat(app.start())
                .hasRightValueSatisfying(appInfos -> {
                    assertThat(app.running()).isTrue();
                    assertThat(appInfos.getController()).isNotNull();
                    assertThat(appInfos.getAvailableCommands()).isNotNull();
                });
    }

    @Test
    void notStartIfSystemIsNotStarting() {
        var expectedSystemFailure =
                "[System failure] Issue turning on system\n" +
                        "System is not usable. It needs more magical power.";

        VavrAssertions.assertThat(appWithFailingSystem().start())
                .hasLeftValueSatisfying(failure ->
                        assertThat(failure.failingBecause()).isEqualTo(expectedSystemFailure)
                );
    }

    @Test
    void notStopIfAppIsNotOn() {
        var expectedSystemFailure =
                "[System failure] Issue turning off system\n" +
                        "The sleigh is not started. Please start the sleigh before any other action...";

        VavrAssertions.assertThat(new App().stop())
                .hasLeftValueSatisfying(failure ->
                        assertThat(failure.failingBecause()).isEqualTo(expectedSystemFailure)
                );
    }

    @Test
    void stopSuccessfully() {
        var app = startingApp();

        VavrAssertions.assertThat(app.stop())
                .hasRightValueSatisfying(appInfos ->
                        assertThat(app.running()).isFalse()
                );
    }

    public App startingApp() {
        var app = new App();
        app.start();
        return app;
    }

    public App appWithFailingSystem() {
        return new App(new ControlSystem(
                new MagicPowerGenerator(
                        new BestMagicalPerformancePowerUnitFactory(
                                List.empty(),
                                HashMap.empty()).bringAllReindeers()),
                XmasSleigh.defaultPosition(),
                new ConsoleDashboard()
        ));
    }
}
