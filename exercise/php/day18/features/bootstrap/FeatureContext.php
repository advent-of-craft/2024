<?php

use Behat\Behat\Context\Context;
use Game\RockPaperScissors;

class FeatureContext implements Context
{
    private string $player1Choice;
    private string $player2Choice;
    private array $result;

    /**
     * @Given Player :player chooses :choice
     */
    public function playerChooses($player, $choice): void
    {
        if ($player == 1) {
            $this->player1Choice = $choice;
        } else {
            $this->player2Choice = $choice;
        }
    }

    /**
     * @When they play
     */
    public function theyPlay(): void
    {
        $this->result = RockPaperScissors::play($this->player1Choice, $this->player2Choice);
    }

    /**
     * @Then the result should be :expectedWinner because :expectedReason
     */
    public function theResultShouldBeBecause($expectedWinner, $expectedReason): void
    {
        expect($this->result['winner'])->toBe($expectedWinner)
            ->and($this->result['reason'])->toBe($expectedReason);
    }
}
