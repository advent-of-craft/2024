<?php

namespace Gifts;

class Toy
{
    public string $description;

    public function __construct(string $description)
    {
        $this->description = $description;
    }

    public function equals(Toy $other): bool
    {
        return $this->description === $other->description;
    }
}