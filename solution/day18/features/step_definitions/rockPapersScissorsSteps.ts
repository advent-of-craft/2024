import {Given, Then, When} from "@cucumber/cucumber";
import {Choice, Result, RockPaperScissorsLizardSpock} from "../../src/rockPaperScissorsLizardSpock";
import * as assert from "assert";

let result: Result;
let player1: Choice;
let player2: Choice;

Given(/^Player (\d+) chooses (.*)$/, function (player, choice) {
    if (player === 1) player1 = choice;
    else player2 = choice;
});

When(/^they play$/, function () {
    result = RockPaperScissorsLizardSpock.play(player1, player2)
});

Then(/^the result should be (.*) because (.*)$/, function (expectedWinner, expectedReason) {
    assert.deepEqual(result, {winner: expectedWinner, reason: expectedReason});
});