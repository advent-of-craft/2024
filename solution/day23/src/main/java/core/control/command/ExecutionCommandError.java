package core.control.command;

import core.control.sleigh.Action;

public class ExecutionCommandError extends ExecutionCommandResult {
    private final String errorMessage;

    public ExecutionCommandError(
            Action sleighStatus,
            String errorMessage) {
        super(sleighStatus);
        this.errorMessage = errorMessage;
    }


    public String getErrorMessage() {
        return errorMessage;
    }
}
