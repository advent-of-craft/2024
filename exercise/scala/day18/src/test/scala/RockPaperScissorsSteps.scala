import game.{Choice, Result, RockPaperScissors, Winner}
import io.cucumber.scala.{EN, ScalaDsl}
import org.scalatest.matchers.should.Matchers

class RockPaperScissorsSteps extends ScalaDsl with EN with Matchers {
  private var result: Result = _
  private var player1: Choice = _
  private var player2: Choice = _

  private def parseChoice(choice: String): Choice = choice match {
    case "🪨" => Choice.ROCK
    case "📄" => Choice.PAPER
    case "✂️" => Choice.SCISSORS
    case _ => throw new IllegalArgumentException("Invalid choice")
  }

  private def parseWinner(winner: String): Winner = winner match {
    case "Player 1" => Winner.PLAYER_1
    case "Player 2" => Winner.PLAYER_2
    case "Draw" => Winner.DRAW
    case _ => throw new IllegalArgumentException("Invalid winner")
  }

  Given("""^Player (\d+) chooses (.*)$""") { (player: Int, choice: String) =>
    val selectedChoice = parseChoice(choice)
    if (player == 1) player1 = selectedChoice else player2 = selectedChoice
  }

  When("""^they play$""") { () =>
    result = RockPaperScissors.play(player1, player2)
  }

  Then("""^the result should be (.*) because (.*)$""") { (expectedWinner: String, expectedReason: String) =>
    result.winner shouldEqual parseWinner(expectedWinner)
    result.reason shouldEqual expectedReason
  }
}