package app;

import core.control.command.ExecutionCommandError;
import core.control.command.ExecutionCommandResult;

import java.util.Scanner;
import java.util.function.Consumer;

import static java.lang.System.in;
import static java.lang.System.out;

public class Main {
    // TODO: inject logger
    private static final Consumer<String> output = out::println;

    private static void log(String message) {
        output.accept(message);
    }

    public static void main(String[] args) {
        var app = new App();
        app.start()
                .peek(startingInfo -> startApp(app, startingInfo))
                .peekLeft(e -> log("Issue starting application: " + e));
    }

    private static void startApp(App app, AppStartingInfo startingInfo) {
        try (var scanner = new Scanner(in)) {
            var commandController = startingInfo.getController();

            log(displayCommand(startingInfo.getAvailableCommands()));

            do {
                commandController.executeCommand(scanner.nextLine())
                        .peek(Main::printExecutionResult)
                        .peekLeft(Main::printError);
            } while (app.running());
        }
    }

    private static void printExecutionResult(ExecutionCommandResult executionResult) {
        log("Sleigh current status: " + executionResult.getSleighStatus() + "\n" +
                displayCommand(executionResult.getAvailableCommandsString())
        );
    }

    private static void printError(ExecutionCommandError error) {
        log("Error: " + error.getErrorMessage() + "\n" +
                displayCommand(error.getAvailableCommandsString())
        );
    }

    private static String displayCommand(String availableCommands) {
        return "Enter a command (" + availableCommands + "): ";
    }
}