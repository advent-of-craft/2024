package core.control.command;

import core.control.sleigh.Action;
import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.collection.Seq;

public enum Command {
    ASCEND("ascend", "a"),
    DESCEND("descend", "d"),
    PARK("park", "p"),
    STOP("stop", "s");

    private static final Map<Action, Seq<Command>> allowedTransitions = HashMap.of(
            Action.PARKED, List.of(Command.ASCEND, Command.STOP),
            Action.FLYING, List.of(Command.DESCEND),
            Action.HOVERING, List.of(Command.ASCEND, Command.PARK)
    );
    private final String primaryKey;
    private final String secondaryKey;

    Command(String primaryKey, String secondaryKey) {
        this.primaryKey = primaryKey;
        this.secondaryKey = secondaryKey;
    }

    public static boolean isAllowedFor(Command c, Action currentStatus) {
        return getCommandByStatus(currentStatus)
                .exists(c::equals);
    }

    public static Commands allowedCommands(Action currentStatus) {
        return Commands.from(getCommandByStatus(currentStatus));
    }

    private static Seq<Command> getCommandByStatus(Action currentStatus) {
        return allowedTransitions.get(currentStatus)
                .getOrElse(List.empty());
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public String getSecondaryKey() {
        return secondaryKey;
    }

    @Override
    public String toString() {
        return getPrimaryKey() + " (" + getSecondaryKey() + ")";
    }
}
