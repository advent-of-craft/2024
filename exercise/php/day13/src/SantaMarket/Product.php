<?php

namespace SantaMarket;

class Product
{
    private string $name;
    private ProductUnit $unit;

    public function __construct(string $name, ProductUnit $unit)
    {
        $this->name = $name;
        $this->unit = $unit;
    }

    public function getName(): string
    {
        return $this->name;
    }

    public function getUnit(): ProductUnit
    {
        return $this->unit;
    }

    public function equals(Product $other): bool
    {
        return $this->name === $other->name && $this->unit === $other->unit;
    }
}
