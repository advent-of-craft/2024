package game;

public class RockPaperScissors {
    private RockPaperScissors() {
    }

    public static Result play(Choice player1, Choice player2) {
        if (player1 == player2) return new Result(Winner.DRAW, "same choice");
        else if (player1 == Choice.ROCK && player2 == Choice.SCISSORS)
            return new Result(Winner.PLAYER_1, "rock crushes scissors");
        else if (player1 == Choice.PAPER && player2 == Choice.ROCK)
            return new Result(Winner.PLAYER_1, "paper covers rock");
        else if (player1 == Choice.SCISSORS && player2 == Choice.PAPER)
            return new Result(Winner.PLAYER_1, "scissors cuts paper");
        else if (player2 == Choice.ROCK && player1 == Choice.SCISSORS)
            return new Result(Winner.PLAYER_2, "rock crushes scissors");
        else if (player2 == Choice.PAPER && player1 == Choice.ROCK)
            return new Result(Winner.PLAYER_2, "paper covers rock");
        else
            return new Result(Winner.PLAYER_2, "scissors cuts paper");
    }
}