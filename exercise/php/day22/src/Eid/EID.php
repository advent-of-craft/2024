<?php

namespace Eid;

use Monad\Option;
use Monad\Option\Some;

class EID
{
    private Sex $sex;
    private Year $year;
    private SerialNumber $serialNumber;

    function __construct(Sex $sex, Year $year, SerialNumber $serialNumber)
    {
        $this->sex = $sex;
        $this->year = $year;
        $this->serialNumber = $serialNumber;
    }

    public static function parse(string $potentialEID): Option
    {
        $sex = Sex::parse($potentialEID[0]);
        if ($sex instanceof None) {
            return $sex;
        }

        $year = Year::parse(substr($potentialEID, 1, 2));
        if ($year instanceof None) {
            return $year;
        }

        $serialNumber = SerialNumber::parse(substr($potentialEID, 3, 3));
        if ($serialNumber instanceof None) {
            return $serialNumber;
        }

        $eid = new EID($sex->value(), $year->value(), $serialNumber->value());

        if ($eid->validateKey(substr($potentialEID, 6))) {
            return new Some(value: $eid);
        } else {
            return new Some(new ParsingError("Invalid key"));
        }
    }

    private function validateKey(string $potentialKey): bool
    {
        return intval($potentialKey) === $this->key();
    }

    public function key(): int
    {
        return 97 - ($this->toLong() % 97);
    }

    private function toLong(): int
    {
        return intval($this->stringWithoutKey());
    }

    private function stringWithoutKey(): string
    {
        return "{$this->sex->toString()}{$this->year}{$this->serialNumber}";
    }

    public function __toString(): string
    {
        return $this->stringWithoutKey() . sprintf("%02d", $this->key());
    }
}