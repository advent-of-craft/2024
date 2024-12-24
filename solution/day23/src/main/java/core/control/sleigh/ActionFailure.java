package core.control.sleigh;

import io.vavr.control.Either;

public class ActionFailure {
    private final String errorMessage;

    public ActionFailure(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public static Either<ActionFailure, ActionSuccess> because(String errorMessage) {
        return Either.left(new ActionFailure(errorMessage));
    }

    public String failingBecause() {
        return errorMessage;
    }
}
