package core.control;

import core.control.sleigh.ActionFailure;
import core.control.sleigh.ActionSuccess;
import io.vavr.control.Either;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

import static core.control.sleigh.Action.*;

public class ControlSystem {
    private final Sleigh sleigh;
    private final Dashboard dashboard;
    private final PowerGenerator magicConcentrator;

    public ControlSystem(PowerGenerator magicConcentrator, Sleigh sleigh, Dashboard dashboard) {
        this.sleigh = sleigh;
        this.dashboard = dashboard;
        this.magicConcentrator = magicConcentrator;
    }

    public Either<ActionFailure, ActionSuccess> startSystem() {
        return when(sleigh::engineIsOff,
                this::checkEnoughPowerToStart,
                SystemErrors.SLEIGH_ALREADY_STARTED);
    }

    private Either<ActionFailure, ActionSuccess> checkEnoughPowerToStart() {
        return when(magicConcentrator.hasEnoughPowerToReach(SystemConfig.XMAS_SPIRIT),
                this::safeStart,
                SystemErrors.NOT_ENOUGH_MAGICAL_POWER);
    }

    private Either<ActionFailure, ActionSuccess> safeStart() {
        logout("Starting the sleigh...");

        return sleigh.turnOn()
                .flatMap(this::checkSystemIsOn)
                .mapLeft(e -> e);
    }

    private Either<ActionFailure, ActionSuccess> checkSystemIsOn(ActionSuccess actionResult) {
        return when(sleigh::engineIsOn,
                sendConfirmationAfterSuccess(actionResult, SystemMessage.SLEIGH_HAS_STARTED),
                SystemErrors.SLEIGH_DID_NOT_START);
    }

    public Either<ActionFailure, ActionSuccess> ascend() {
        return whenSystemIsOn(this::safeAscend);
    }

    private Either<ActionFailure, ActionSuccess> safeAscend() {
        return when(magicConcentrator.hasEnoughPowerToReach(SystemConfig.XMAS_SPIRIT),
                this::harnessPowerAndFly,
                SystemErrors.REINDEER_NEEDS_REST);
    }

    private Either<ActionFailure, ActionSuccess> harnessPowerAndFly() {
        magicConcentrator.harnessAllPower();

        logout("Ascending...");

        return sleigh.fly()
                .flatMap(this::checkSleighIsFlying)
                .mapLeft(e -> e);
    }

    private Either<ActionFailure, ActionSuccess> checkSleighIsFlying(ActionSuccess actionResult) {
        return sleigh.is(FLYING, actionResult);
    }

    public Either<ActionFailure, ActionSuccess> descend() {
        return whenSystemIsOn(this::safeDescend);
    }

    private Either<ActionFailure, ActionSuccess> safeDescend() {
        logout("Descending...");

        return sleigh.hover()
                .flatMap(this::checkSleighIsHovering)
                .mapLeft(e -> e);
    }

    private Either<ActionFailure, ActionSuccess> checkSleighIsHovering(ActionSuccess actionResult) {
        return sleigh.is(HOVERING, actionResult);
    }

    public Either<ActionFailure, ActionSuccess> park() {
        return whenSystemIsOn(this::safePark);
    }

    private Either<ActionFailure, ActionSuccess> safePark() {
        logout("Parking...");

        magicConcentrator.rechargePower();

        return sleigh.park()
                .flatMap(this::checkSleighIsParked)
                .mapLeft(e -> e);
    }

    private Either<ActionFailure, ActionSuccess> checkSleighIsParked(ActionSuccess actionResult) {
        return sleigh.is(PARKED, actionResult);
    }

    public Either<ActionFailure, ActionSuccess> stopSystem() {
        return whenSystemIsOn(this::safeStop);
    }

    private Either<ActionFailure, ActionSuccess> safeStop() {
        logout("Stopping the sleigh...");

        return sleigh.turnOff()
                .flatMap(this::checkSystemIfOff)
                .mapLeft(e -> e);
    }

    private Either<ActionFailure, ActionSuccess> checkSystemIfOff(ActionSuccess result) {
        return when(sleigh::engineIsOff,
                sendConfirmationAfterSuccess(result, SystemMessage.SLEIGH_HAS_STOPPED),
                SystemErrors.SLEIGH_STILL_RUNNING);
    }

    private Either<ActionFailure, ActionSuccess> whenSystemIsOn(Supplier<Either<ActionFailure, ActionSuccess>> actionSupplier) {
        return when(sleigh::engineIsOn,
                actionSupplier,
                SystemErrors.SLEIGH_NOT_STARTED);

    }

    private Either<ActionFailure, ActionSuccess> when(BooleanSupplier condition, Supplier<Either<ActionFailure, ActionSuccess>> action, String errorMessage) {
        return condition.getAsBoolean()
                ? action.get()
                : ActionFailure.because(errorMessage);
    }

    private Supplier<Either<ActionFailure, ActionSuccess>> sendConfirmationAfterSuccess(ActionSuccess actionResult, String confirmationMessage) {
        logout(confirmationMessage);
        return () -> Either.right(actionResult);
    }

    private void logout(String message) {
        dashboard.display(message);
    }
}

