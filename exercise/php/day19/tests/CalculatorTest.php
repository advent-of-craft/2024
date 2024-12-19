<?php

use Travel\SantaTravelCalculator;

it('should calculate the distance correctly', function ($numberOfReindeers, $expectedDistance) {
    expect(SantaTravelCalculator::calculateTotalDistanceRecursively($numberOfReindeers))->toBe($expectedDistance);
})->with([
    [1, 1],
    [2, 3],
    [5, 31],
    [10, 1023],
    [20, 1048575],
    [30, 1073741823]
]);

it('should fail for numbers greater than 32', function ($numberOfReindeers) {
    expect(fn() => SantaTravelCalculator::calculateTotalDistanceRecursively($numberOfReindeers))->toThrow(OverflowException::class);
})->with([32, 50]);
