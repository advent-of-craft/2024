from behave import given, when, then

from game.game import RockPaperScissors, Choice, Winner


def parse_choice(choice: str) -> Choice:
    return {
        "🪨": Choice.ROCK,
        "📄": Choice.PAPER,
        "✂️": Choice.SCISSORS
    }[choice]


def parse_winner(winner: str) -> Winner:
    return {
        "Player 1": Winner.PLAYER_1,
        "Player 2": Winner.PLAYER_2,
        "Draw": Winner.DRAW
    }[winner]


@given('Player {player} chooses {choice}')
def step_given_player_chooses(context, player, choice):
    selected_choice = parse_choice(choice)
    if player == '1':
        context.player1 = selected_choice
    elif player == '2':
        context.player2 = selected_choice


@when('they play')
def step_when_they_play(context):
    context.result = RockPaperScissors.play(context.player1, context.player2)


@then('the result should be {expected_winner} because {expected_reason}')
def step_then_result_should_be(context, expected_winner, expected_reason):
    result = context.result
    assert result.winner == parse_winner(expected_winner), f"Expected {expected_winner} but got {result.winner}"
    assert result.reason == expected_reason, f"Expected '{expected_reason}' but got '{result.reason}'"
