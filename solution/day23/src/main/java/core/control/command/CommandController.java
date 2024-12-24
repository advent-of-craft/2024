package core.control.command;

import core.control.sleigh.Action;
import core.control.sleigh.ActionFailure;
import core.control.sleigh.ActionSuccess;
import io.vavr.control.Either;

import java.util.function.Supplier;


public class CommandController {
    private final CommandMap commandMap;
    private Action sleightStatus;

    public CommandController(
            CommandMap commandMap,
            Action sleightStatus) {
        this.commandMap = commandMap;
        this.sleightStatus = sleightStatus;
    }

    public Either<ExecutionCommandError, ExecutionCommandResult> executeCommand(String commandKey) {
        return commandMap
                .getCommand(commandKey, sleightStatus)
                .flatMap(this::executeAction)
                .mapLeft(this::createExecutionCommandError);
    }

    private Either<ActionFailure, ExecutionCommandResult> executeAction(Supplier<Either<ActionFailure, ActionSuccess>> actionSupplier) {
        return actionSupplier.get()
                .map(this::createExecutionCommandResult)
                .mapLeft(e -> new ActionFailure("Issue when executing command: " + e.failingBecause()));
    }

    private ExecutionCommandResult createExecutionCommandResult(ActionSuccess actionResult) {
        sleightStatus = actionResult.getSleighStatus();
        return new ExecutionCommandResult(sleightStatus);
    }

    private ExecutionCommandError createExecutionCommandError(ActionFailure error) {
        return new ExecutionCommandError(sleightStatus, error.failingBecause());
    }


    public Commands getAvailableCommands() {
        return Command.allowedCommands(sleightStatus);
    }
}
