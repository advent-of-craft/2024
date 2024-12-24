package core.control;

import core.control.sleigh.Action;
import core.control.sleigh.ActionFailure;
import core.control.sleigh.ActionSuccess;
import io.vavr.control.Either;

public interface Sleigh {
    boolean engineIsOn();

    boolean engineIsOff();

    Either<ActionFailure, ActionSuccess> turnOn();

    Either<ActionFailure, ActionSuccess> turnOff();

    Either<ActionFailure, ActionSuccess> fly();

    Either<ActionFailure, ActionSuccess> hover();

    Either<ActionFailure, ActionSuccess> park();

    Either<ActionFailure, ActionSuccess> is(Action action, ActionSuccess result);
}
