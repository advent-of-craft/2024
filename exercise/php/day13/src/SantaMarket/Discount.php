<?php

namespace SantaMarket;

class Discount
{
    private string $description;
    private float $discountAmount;
    private Product $product;

    public function __construct(Product $product, string $description, float $discountAmount)
    {
        $this->product = $product;
        $this->description = $description;
        $this->discountAmount = $discountAmount;
    }

    public function equals(Discount $other, float $tolerance = 0.001): bool
    {
        return $this->product->equals($other->getProduct()) &&
            $this->description === $other->getDescription() &&
            $this->isCloseEnough($this->discountAmount, $other->getDiscountAmount(), $tolerance);
    }

    public function getProduct(): Product
    {
        return $this->product;
    }

    public function getDescription(): string
    {
        return $this->description;
    }

    private function isCloseEnough(float $a, float $b, float $tolerance): bool
    {
        return abs($a - $b) <= $tolerance;
    }

    public function getDiscountAmount(): float
    {
        return $this->discountAmount;
    }
}