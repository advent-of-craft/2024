<?php

namespace Gifts;

class Child
{
    public string $name;
    public string $behavior;
    public array $wishlist;

    public function __construct(string $name, string $behavior)
    {
        $this->name = $name;
        $this->behavior = $behavior;
        $this->wishlist = [];
    }

    public function setWishlist(Toy $firstChoice, Toy $secondChoice, Toy $thirdChoice): void
    {
        $this->wishlist = [$firstChoice, $secondChoice, $thirdChoice];
    }
}
