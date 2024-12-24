package core.control.sleigh;

import io.vavr.control.Either;

public class ActionSuccess {
    private final EngineStatus status;
    private final Action action;

    private ActionSuccess(EngineStatus status, Action action) {
        this.status = status;
        this.action = action;
    }

    public static Either<ActionFailure, ActionSuccess> sleighTurnedOff() {
        return Either.right(new ActionSuccess(EngineStatus.OFF, Action.PARKED));
    }

    public static Either<ActionFailure, ActionSuccess> sleighParked() {
        return Either.right(new ActionSuccess(EngineStatus.ON, Action.PARKED));
    }

    public static Either<ActionFailure, ActionSuccess> sleighFlying() {
        return Either.right(new ActionSuccess(EngineStatus.ON, Action.FLYING));
    }

    public static Either<ActionFailure, ActionSuccess> actionSuccess(EngineStatus status, Action action) {
        return Either.right(new ActionSuccess(status, action));
    }

    public EngineStatus getEngineStatus() {
        return status;
    }

    public Action getSleighStatus() {
        return action;
    }
}
