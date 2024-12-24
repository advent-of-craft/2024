package core.control.sleigh;

import core.control.Sleigh;
import core.control.SystemErrors;
import io.vavr.control.Either;

public class XmasSleigh implements Sleigh {
    private EngineStatus status;
    private Action action;

    public XmasSleigh(EngineStatus status, Action action) {
        this.status = status;
        this.action = action;
    }

    public static XmasSleigh defaultPosition() {
        return new XmasSleigh(EngineStatus.OFF, Action.PARKED);
    }

    @Override
    public boolean engineIsOn() {
        return status == EngineStatus.ON;
    }

    @Override
    public boolean engineIsOff() {
        return status == EngineStatus.OFF;
    }

    @Override
    public Either<ActionFailure, ActionSuccess> turnOn() {
        status = EngineStatus.ON;

        return createActionResult();
    }

    @Override
    public Either<ActionFailure, ActionSuccess> turnOff() {
        status = EngineStatus.OFF;

        return createActionResult();
    }

    @Override
    public Either<ActionFailure, ActionSuccess> fly() {
        action = Action.FLYING;

        return createActionResult();
    }

    @Override
    public Either<ActionFailure, ActionSuccess> hover() {
        action = Action.HOVERING;

        return createActionResult();
    }

    @Override
    public Either<ActionFailure, ActionSuccess> park() {
        action = Action.PARKED;

        return createActionResult();
    }

    @Override
    public Either<ActionFailure, ActionSuccess> is(Action action, ActionSuccess result) {
        return this.action == action
                ? Either.right(result)
                : ActionFailure.because(SystemErrors.failingTo(action));
    }

    private Either<ActionFailure, ActionSuccess> createActionResult() {
        return ActionSuccess.actionSuccess(status, action);
    }
}
