package core.control.command;

import core.control.sleigh.Action;
import core.control.sleigh.ActionFailure;
import core.control.sleigh.ActionSuccess;
import io.vavr.collection.*;
import io.vavr.control.Either;
import org.assertj.vavr.api.VavrAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.function.Supplier;

import static core.control.command.Command.*;
import static core.control.sleigh.Action.*;
import static io.vavr.control.Either.left;
import static org.assertj.core.api.Assertions.assertThat;

class CommandControllerTest {
    private static CommandController createController(Map<Command, Supplier<Either<ActionFailure, ActionSuccess>>> withCommands, Action defaultPosition) {
        return new CommandController(
                new CommandMap(withCommands),
                defaultPosition);
    }

    public static Stream<Arguments> validTransitions() {
        return Stream.of(
                Arguments.of(PARKED, List.of(ASCEND, STOP)),
                Arguments.of(FLYING, List.of(DESCEND)),
                Arguments.of(HOVERING, List.of(PARK, ASCEND))
        );
    }

    @Test
    void executeACommandAndSendSystemStatusIfSuccess() {
        VavrAssertions.assertThat(createController(HashMap.of(ASCEND, ActionSuccess::sleighFlying), PARKED)
                .executeCommand("a")
        ).hasRightValueSatisfying(result -> {
            assertThat(result.getSleighStatus()).isEqualTo(FLYING.toString());
            assertThat(result.getAvailableCommandsString()).isEqualTo(List.of(DESCEND).mkString());
        });
    }

    @Test
    void executeACommandAndDisplayErrorMessageIfFailure() {
        var expectedErrorMessage = "System wide error";
        VavrAssertions.assertThat(createController(HashMap.of(ASCEND, () -> left(new ActionFailure(expectedErrorMessage))), PARKED)
                .executeCommand("a")
        ).hasLeftValueSatisfying(e ->
                assertThat(e.getErrorMessage()).isEqualTo("Issue when executing command: " + expectedErrorMessage)
        );
    }

    @Test
    void notExecuteCommandIfNotAvailable() {
        VavrAssertions.assertThat(createController(HashMap.of(PARK, ActionSuccess::sleighParked), PARKED)
                .executeCommand("bark")
        ).hasLeftValueSatisfying(e ->
                assertThat(e.getErrorMessage()).isEqualTo("Invalid command. Please try again.")
        );
    }

    @Test
    void notExecuteCommandIfNotAllowed() {
        VavrAssertions.assertThat(createController(HashMap.of(PARK, ActionSuccess::sleighParked), PARKED)
                .executeCommand(ASCEND.getPrimaryKey())
        ).hasLeftValueSatisfying(e ->
                assertThat(e.getErrorMessage()).isEqualTo("Invalid command. Please try again.")
        );
    }

    @ParameterizedTest
    @MethodSource("validTransitions")
    void getAvailableCommandsForStatus(Action action, Seq<Command> expectedCommands) {
        var commands = createController(HashMap.of(ASCEND, ActionSuccess::sleighFlying), action)
                .getAvailableCommands();

        assertThat(commands.getList())
                .containsExactlyInAnyOrderElementsOf(expectedCommands);
    }
}
