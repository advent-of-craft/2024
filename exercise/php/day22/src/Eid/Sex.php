<?php

namespace Eid;

use Monad\Option;
use Monad\Option\None;
use Monad\Option\Some;

enum Sex: string
{
    case SLOUBI = '1';
    case GAGNA = '2';
    case CATACT = '3';

    public static function parse(string $potentialSex): Option
    {
        return match ($potentialSex) {
            '1' => new Some(self::SLOUBI),
            '2' => new Some(self::GAGNA),
            '3' => new Some(self::CATACT),
            default => new None(new ParsingError("Not a valid sex")),
        };
    }

    public function toString(): string
    {
        return $this->value;
    }
}