from enum import Enum
from typing import NamedTuple


class Winner(Enum):
    PLAYER_1 = "Player 1"
    PLAYER_2 = "Player 2"
    DRAW = "Draw"


class Choice(Enum):
    ROCK = "🪨"
    PAPER = "📄"
    SCISSORS = "✂️"


class Result(NamedTuple):
    winner: Winner
    reason: str


class RockPaperScissors:
    @staticmethod
    def play(player1: Choice, player2: Choice) -> Result:
        if player1 == player2:
            return Result(Winner.DRAW, "same choice")
        if player1 == Choice.ROCK and player2 == Choice.SCISSORS:
            return Result(Winner.PLAYER_1, "rock crushes scissors")
        if player1 == Choice.PAPER and player2 == Choice.ROCK:
            return Result(Winner.PLAYER_1, "paper covers rock")
        if player1 == Choice.SCISSORS and player2 == Choice.PAPER:
            return Result(Winner.PLAYER_1, "scissors cuts paper")
        if player2 == Choice.ROCK and player1 == Choice.SCISSORS:
            return Result(Winner.PLAYER_2, "rock crushes scissors")
        if player2 == Choice.PAPER and player1 == Choice.ROCK:
            return Result(Winner.PLAYER_2, "paper covers rock")
        return Result(Winner.PLAYER_2, "scissors cuts paper")
