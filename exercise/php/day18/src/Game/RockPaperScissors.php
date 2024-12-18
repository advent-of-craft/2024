<?php

namespace Game;

class RockPaperScissors
{
    public static function play(string $player1, string $player2): array
    {
        if ($player1 === $player2) {
            return [
                'winner' => 'Draw',
                'reason' => 'same choice'
            ];
        } else if ($player1 === '🪨' && $player2 === '✂️') {
            return [
                'winner' => 'Player 1',
                'reason' => 'rock crushes scissors'
            ];
        } else if ($player1 === '📄' && $player2 === '🪨') {
            return [
                'winner' => 'Player 1',
                'reason' => 'paper covers rock'
            ];
        } else if ($player1 === '✂️' && $player2 === '📄') {
            return [
                'winner' => 'Player 1',
                'reason' => 'scissors cuts paper'
            ];
        } else if ($player2 === '🪨' && $player1 === '✂️') {
            return [
                'winner' => 'Player 2',
                'reason' => 'rock crushes scissors'
            ];
        } else if ($player2 === '📄' && $player1 === '🪨') {
            return [
                'winner' => 'Player 2',
                'reason' => 'paper covers rock'
            ];
        } else {
            return [
                'winner' => 'Player 2',
                'reason' => 'scissors cuts paper'
            ];
        }
    }
}
