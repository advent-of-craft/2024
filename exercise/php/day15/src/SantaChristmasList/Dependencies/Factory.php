<?php

namespace SantaChristmasList\Dependencies;

use SantaChristmasList\Models\Gift;
use SantaChristmasList\Models\ManufacturedGift;

class Factory extends \ArrayObject
{
    public function findManufacturedGift(Gift $gift): ?ManufacturedGift
    {
        return $this->offsetExists($gift->getName()) ? $this->offsetGet($gift->getName()) : null;
    }

    public function set(Gift $gift, ManufacturedGift $manufacturedGift): void
    {
        $this->offsetSet($gift->getName(), $manufacturedGift);
    }
}