<?php

namespace Eid;

use InvalidArgumentException;
use Monad\Option;
use Monad\Option\None;
use Monad\Option\Some;

class Year
{
    private int $value;

    public function __construct(int $value)
    {
        if (!self::isValid($value)) {
            throw new InvalidArgumentException("Year should be between 0 and 99");
        }
        $this->value = $value;
    }

    public static function isValid(int $value): bool
    {
        return $value >= 0 && $value <= 99;
    }

    public static function parse(string $input): Option
    {
        $value = IntFunctions::toInt($input);
        if ($value !== null && self::isValid($value)) {
            return Some::create(new Year($value));
        }
        return new None(new ParsingError("Year should be positive and less than 100"));
    }

    public function __toString()
    {
        return sprintf("%02d", $this->value);
    }
}
