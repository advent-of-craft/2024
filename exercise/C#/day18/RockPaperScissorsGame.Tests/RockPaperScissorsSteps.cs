using FluentAssertions;
using TechTalk.SpecFlow;

namespace RockPaperScissorsGame.Tests
{
    [Binding]
    public class RockPaperScissorsSteps
    {
        private Result? _result;
        private Choice _player1Choice;
        private Choice _player2Choice;

        [Given(@"Player (\d+) chooses (.*)")]
        public void GivenPlayerChooses(int player, string choice)
        {
            var parsedChoice = ParseChoice(choice);

            if (player == 1) _player1Choice = parsedChoice;
            else _player2Choice = parsedChoice;
        }

        [When(@"they play")]
        public void WhenTheyPlay() => _result = RockPaperScissors.Play(_player1Choice, _player2Choice);

        [Then(@"the result should be (.*) because (.*)")]
        public void ThenTheResultShouldBeBecause(string expectedWinner, string expectedReason)
        {
            _result!.Winner.Should().Be(
                ParseWinner(expectedWinner)
            );
            _result.Reason.Should().Be(expectedReason);
        }

        private static Choice ParseChoice(string choice)
            => choice switch
            {
                "🪨" => Choice.Rock,
                "📄" => Choice.Paper,
                "✂️" => Choice.Scissors,
                _ => throw new ArgumentException("Invalid choice")
            };

        private static Winner ParseWinner(string winner)
            => winner switch
            {
                "Player 1" => Winner.Player1,
                "Player 2" => Winner.Player2,
                "Draw" => Winner.Draw,
                _ => throw new ArgumentException("Invalid winner")
            };
    }
}