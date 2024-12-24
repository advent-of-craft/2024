package core.control.command;

import core.control.sleigh.Action;

public class ExecutionCommandResult {
    private final Action sleighStatus;
    private final Commands availableCommands;

    public ExecutionCommandResult(Action sleighStatus) {
        this.sleighStatus = sleighStatus;
        this.availableCommands = Command.allowedCommands(sleighStatus);
    }

    public String getSleighStatus() {
        return sleighStatus.toString();
    }

    public String getAvailableCommandsString() {
        return availableCommands.toString();
    }
}
