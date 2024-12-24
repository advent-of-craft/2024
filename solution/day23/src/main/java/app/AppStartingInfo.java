package app;

import core.control.command.CommandController;
import core.control.sleigh.ActionFailure;
import io.vavr.control.Either;

public class AppStartingInfo {
    public static Either<ActionFailure, AppStartingInfo> appStarted(CommandController controller) {
        return Either.right(new AppStartingInfo(controller));
    }

    private final CommandController controlCommands;

    public AppStartingInfo(CommandController controlCommands) {
        this.controlCommands = controlCommands;
    }

    public String getAvailableCommands() {
        return controlCommands
                .getAvailableCommands()
                .toString();
    }

    public CommandController getController(){
        return controlCommands;
    }
}
