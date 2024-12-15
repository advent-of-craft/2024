<?php

namespace SantaChristmasList\Dependencies;

use SantaChristmasList\Models\Gift;

class Inventory extends \ArrayObject
{
    public function pickUpGift(string $barCode): ?Gift
    {
        return $this->offsetExists($barCode) ? $this->offsetGet($barCode) : null;
    }

    public function set(string $string, Gift $toy): void
    {
        $this->offsetSet($string, $toy);
    }
}