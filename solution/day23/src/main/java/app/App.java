package app;

import core.control.ControlSystem;
import core.control.Dashboard;
import core.control.command.Command;
import core.control.command.CommandController;
import core.control.command.CommandMap;
import core.control.dashboard.ConsoleDashboard;
import core.control.generator.AmplifierType;
import core.control.generator.MagicPowerGenerator;
import core.control.generator.factory.BestMagicalPerformancePowerUnitFactory;
import core.control.sleigh.ActionFailure;
import core.control.sleigh.ActionSuccess;
import core.control.sleigh.XmasSleigh;
import external.deer.Reindeer;
import external.stable.MagicStable;
import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.collection.Seq;
import io.vavr.control.Either;

import static app.AppStartingInfo.appStarted;
import static core.control.sleigh.ActionSuccess.sleighTurnedOff;

public class App {
    public static final MagicStable magicStable = new MagicStable();
    private static final Dashboard defaultDashboard = new ConsoleDashboard();
    private final ControlSystem controlSystem;
    private final CommandMap availableCommands;
    private boolean isRunning = false;

    public App() {
        this(new ControlSystem(
                new MagicPowerGenerator(
                        new BestMagicalPerformancePowerUnitFactory(
                                getAllReindeers(),
                                getAvailableAmplifiers()).bringAllReindeers()),
                XmasSleigh.defaultPosition(),
                defaultDashboard
        ));
    }

    private static Seq<Reindeer> getAllReindeers() {
        return List.ofAll(magicStable.getAllReindeers());
    }

    public App(ControlSystem controlSystem) {
        this.controlSystem = controlSystem;

        availableCommands = new CommandMap(HashMap.of(
                Command.ASCEND, controlSystem::ascend,
                Command.DESCEND, controlSystem::descend,
                Command.PARK, controlSystem::park,
                Command.STOP, this::stop
        ));
    }

    private static Map<Integer, AmplifierType> getAvailableAmplifiers() {
        return HashMap.of(
                1, AmplifierType.DIVINE,
                2, AmplifierType.BLESSED,
                3, AmplifierType.BLESSED);
    }

    public Either<ActionFailure, AppStartingInfo> start() {
        return controlSystem.startSystem()
                .flatMap(this::startApplication)
                .mapLeft(this::displayIssueTurningOnSystem);
    }

    private ActionFailure displayIssueTurningOnSystem(ActionFailure failure) {
        return new ActionFailure(
                "[System failure] " +
                        AppFailure.ISSUE_TURNING_ON_SYSTEM + "\n" +
                        failure.failingBecause());
    }

    private Either<ActionFailure, AppStartingInfo> startApplication(ActionSuccess sleighActionResult) {
        isRunning = true;

        return appStarted(
                new CommandController(
                        availableCommands,
                        sleighActionResult.getSleighStatus()));
    }

    public Either<ActionFailure, ActionSuccess> stop() {
        return controlSystem.stopSystem()
                .flatMap(this::stopApplication)
                .mapLeft(this::displayIssueShuttingDownSystem);
    }

    private ActionFailure displayIssueShuttingDownSystem(ActionFailure failure) {
        return new ActionFailure(
                "[System failure] " +
                        AppFailure.ISSUE_TURNING_OFF_SYSTEM + "\n" +
                        failure.failingBecause());
    }

    private Either<ActionFailure, ActionSuccess> stopApplication(ActionSuccess sleighActionResult) {
        isRunning = false;
        return sleighTurnedOff();
    }

    public boolean running() {
        return isRunning;
    }

    static class AppFailure {
        private static final String ISSUE_TURNING_OFF_SYSTEM = "Issue turning off system";
        private static final String ISSUE_TURNING_ON_SYSTEM = "Issue turning on system";

        private AppFailure() {
        }
    }
}
