package core.control.command;

import io.vavr.collection.Seq;

public class Commands {
    private final Seq<Command> inner;

    private Commands(Seq<Command> commands) {
        this.inner = commands;
    }

    public static Commands from(Seq<Command> allCommands) {
        return new Commands(allCommands);
    }

    @Override
    public String toString() {
        return inner.map(Command::toString)
                .mkString(", ");
    }

    public Seq<Command> getList() {
        return inner;
    }
}
