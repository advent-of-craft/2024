package game

case class Result(winner: Winner, reason: String)

enum Winner {
  case PLAYER_1, PLAYER_2, DRAW
}

enum Choice(val symbol: String) {
  case ROCK extends Choice("🪨")
  case PAPER extends Choice("📄")
  case SCISSORS extends Choice("✂️")
}

object RockPaperScissors {
  def play(player1: Choice, player2: Choice): Result = if (player1 == player2) Result(Winner.DRAW, "same choice")
  else if (player1 == Choice.ROCK && player2 == Choice.SCISSORS) Result(Winner.PLAYER_1, "rock crushes scissors")
  else if (player1 == Choice.PAPER && player2 == Choice.ROCK) Result(Winner.PLAYER_1, "paper covers rock")
  else if (player1 == Choice.SCISSORS && player2 == Choice.PAPER) Result(Winner.PLAYER_1, "scissors cuts paper")
  else if (player2 == Choice.ROCK && player1 == Choice.SCISSORS) Result(Winner.PLAYER_2, "rock crushes scissors")
  else if (player2 == Choice.PAPER && player1 == Choice.ROCK) Result(Winner.PLAYER_2, "paper covers rock")
  else Result(Winner.PLAYER_2, "scissors cuts paper")
}