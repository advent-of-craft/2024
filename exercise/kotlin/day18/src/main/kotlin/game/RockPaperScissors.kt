package game

data class Result(val winner: Winner, val reason: String)

enum class Winner {
    PLAYER_1,
    PLAYER_2,
    DRAW
}

enum class Choice(val symbol: String) {
    ROCK("🪨"),
    PAPER("📄"),
    SCISSORS("✂️")
}

class RockPaperScissors {
    companion object {
        fun play(player1: Choice, player2: Choice): Result = if (player1 == player2) Result(Winner.DRAW, "same choice")
        else if (player1 == Choice.ROCK && player2 == Choice.SCISSORS) Result(Winner.PLAYER_1, "rock crushes scissors")
        else if (player1 == Choice.PAPER && player2 == Choice.ROCK) Result(Winner.PLAYER_1, "paper covers rock")
        else if (player1 == Choice.SCISSORS && player2 == Choice.PAPER) Result(Winner.PLAYER_1, "scissors cuts paper")
        else if (player2 == Choice.ROCK && player1 == Choice.SCISSORS) Result(Winner.PLAYER_2, "rock crushes scissors")
        else if (player2 == Choice.PAPER && player1 == Choice.ROCK) Result(Winner.PLAYER_2, "paper covers rock")
        else Result(Winner.PLAYER_2, "scissors cuts paper")
    }
}