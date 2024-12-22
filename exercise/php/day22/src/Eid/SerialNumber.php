<?php

namespace Eid;

use InvalidArgumentException;
use Monad\Option;
use Monad\Option\None;
use Monad\Option\Some;

class SerialNumber
{
    private int $value;

    private function __construct(int $value)
    {
        if (!self::isValid($value)) {
            throw new InvalidArgumentException("Serial number should be between 1 and 999");
        }
        $this->value = $value;
    }

    private static function isValid(int $value): bool
    {
        return $value >= 1 && $value <= 999;
    }

    public static function parse(string $input): Option
    {
        $value = IntFunctions::toInt($input);
        if ($value !== null && self::isValid($value)) {
            return Some::create(new SerialNumber($value));
        }
        return new None(new ParsingError("Year should be positive and less than 100"));
    }

    public function __toString()
    {
        return sprintf("%03d", $this->value);
    }
}
