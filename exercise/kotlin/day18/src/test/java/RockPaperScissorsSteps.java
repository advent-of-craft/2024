import game.Choice;
import game.Result;
import game.RockPaperScissors;
import game.Winner;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.assertj.core.api.Assertions.assertThat;

public class RockPaperScissorsSteps {
    private Result result;
    private Choice player1;
    private Choice player2;

    private static Choice parseChoice(String choice) {
        return switch (choice) {
            case "🪨" -> Choice.ROCK;
            case "📄" -> Choice.PAPER;
            case "✂️" -> Choice.SCISSORS;
            default -> throw new IllegalArgumentException("Invalid choice");
        };
    }

    private static Winner parseWinner(String winner) {
        return switch (winner) {
            case "Player 1" -> Winner.PLAYER_1;
            case "Player 2" -> Winner.PLAYER_2;
            case "Draw" -> Winner.DRAW;
            default -> throw new IllegalArgumentException("Invalid winner");
        };
    }

    @Given("^Player (\\d+) chooses (.*)$")
    public void playerChooses(int player, String choice) {
        var selectedChoice = parseChoice(choice);

        if (player == 1) player1 = selectedChoice;
        else player2 = selectedChoice;
    }

    @When("^they play$")
    public void theyPlay() {
        result = RockPaperScissors.Companion.play(player1, player2);
    }

    @Then("^the result should be (.*) because (.*)$")
    public void theResultShouldBe(String expectedWinner, String expectedReason) {
        assertThat(result.getWinner()).isEqualTo(parseWinner(expectedWinner));
        assertThat(result.getReason()).isEqualTo(expectedReason);
    }
}