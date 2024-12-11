<?php

use Christmas\Preparation;
use Christmas\ToyType;

it('should return the correct preparation message based on number of gifts', function ($numberOfGifts, $expected) {
    expect(Preparation::prepareGifts($numberOfGifts))->toBe($expected);
})->with([
    [-1, 'No gifts to prepare.'],
    [0, 'No gifts to prepare.'],
    [1, 'Elves will prepare the gifts.'],
    [49, 'Elves will prepare the gifts.'],
    [50, 'Santa will prepare the gifts.']
]);

it('should return the correct category based on age', function ($age, $expectedCategory) {
    expect(Preparation::categorizeGift($age))->toBe($expectedCategory);
})->with([
    [1, 'Baby'],
    [3, 'Toddler'],
    [6, 'Child'],
    [13, 'Teen']
]);

it('should return the correct balance check based on toy type and count', function ($toyType, $toysCount, $totalToys, $expected) {
    expect(Preparation::ensureToyBalance($toyType, $toysCount, $totalToys))->toBe($expected);
})->with([
    [ToyType::EDUCATIONAL, 25, 100, true],
    [ToyType::FUN, 30, 100, true],
    [ToyType::CREATIVE, 20, 100, true],
    [ToyType::EDUCATIONAL, 20, 100, false],
    [ToyType::FUN, 29, 100, false],
    [ToyType::CREATIVE, 15, 100, false]
]);
