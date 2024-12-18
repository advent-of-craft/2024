namespace RockPaperScissorsGame
{
    public enum Choice
    {
        Rock,
        Paper,
        Scissors
    }

    public enum Winner
    {
        Player1,
        Player2,
        Draw
    }

    public record Result(Winner Winner, string Reason);

    public static class RockPaperScissors
    {
        public static Result? Play(Choice player1, Choice player2)
        {
            if (player1 == player2)
                return new Result(Winner.Draw, "same choice");
            else if (player1 == Choice.Rock && player2 == Choice.Scissors)
                return new Result(Winner.Player1, "rock crushes scissors");
            else if (player1 == Choice.Paper && player2 == Choice.Rock)
                return new Result(Winner.Player1, "paper covers rock");
            else if (player1 == Choice.Scissors && player2 == Choice.Paper)
                return new Result(Winner.Player1, "scissors cuts paper");
            else if (player2 == Choice.Rock && player1 == Choice.Scissors)
                return new Result(Winner.Player2, "rock crushes scissors");
            else if (player2 == Choice.Paper && player1 == Choice.Rock)
                return new Result(Winner.Player2, "paper covers rock");
            else
                return new Result(Winner.Player2, "scissors cuts paper");
        }
    }
}