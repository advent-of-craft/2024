<?php

namespace Travel;

use OverflowException;

const INT32_MAX = 2147483647;

class SantaTravelCalculator
{
    public static function calculateTotalDistanceRecursively(int $numberOfReindeers): int
    {
        if ($numberOfReindeers == 1) return 1;
        $nextValue = 2 * self::calculateTotalDistanceRecursively($numberOfReindeers - 1) + 1;

        if ($nextValue >= INT32_MAX) {
            throw new OverflowException("Overflow for $numberOfReindeers reindeers");
        }
        return $nextValue;
    }
}