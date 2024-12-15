<?php

namespace SantaChristmasList\Dependencies;


use SantaChristmasList\Models\Child;
use SantaChristmasList\Models\Gift;

class WishList extends \ArrayObject
{
    public function identifyGift(Child $child): ?Gift
    {
        return $this->offsetExists($child->getName()) ? $this->offsetGet($child->getName()) : null;
    }

    public function set(Child $child, Gift $gift): void
    {
        $this->offsetSet($child->getName(), $gift);
    }
}