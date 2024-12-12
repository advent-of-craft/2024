<?php

namespace Gifts;

use InvalidArgumentException;

class Santa
{
    private array $childrenRepository = [];

    public function chooseToyForChild(string $childName): ?Toy
    {
        $found = null;
        foreach ($this->childrenRepository as $currentChild) {
            if ($currentChild->name === $childName) {
                $found = $currentChild;
                break;
            }
        }

        if ($found === null) {
            throw new InvalidArgumentException("No such child found");
        }

        switch ($found->behavior) {
            case "naughty":
                return $found->wishlist[2];
            case "nice":
                return $found->wishlist[1];
            case "very nice":
                return $found->wishlist[0];
            default:
                return null;
        }
    }

    public function addChild(Child $child): void
    {
        $this->childrenRepository[] = $child;
    }
}
