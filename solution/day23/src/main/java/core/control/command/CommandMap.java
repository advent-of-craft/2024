package core.control.command;

import core.control.sleigh.Action;
import core.control.sleigh.ActionFailure;
import core.control.sleigh.ActionSuccess;
import io.vavr.collection.Map;
import io.vavr.control.Either;

import java.util.function.Supplier;

import static core.control.command.Command.isAllowedFor;


public class CommandMap {
    Map<Command, Supplier<Either<ActionFailure, ActionSuccess>>> commands;

    public CommandMap(Map<Command, Supplier<Either<ActionFailure, ActionSuccess>>> commands) {
        this.commands = commands;
    }

    public Either<ActionFailure, Supplier<Either<ActionFailure, ActionSuccess>>> getCommand(String commandKeyString, Action currentStatus) {
        return commands.filter(c -> isAllowedFor(c._1, currentStatus))
                .findLast(c -> commandKeyString.equals(c._1().getPrimaryKey()) || commandKeyString.equals(c._1().getSecondaryKey()))
                .map(c -> c._2)
                .toEither(new ActionFailure("Invalid command. Please try again."));
    }
}
