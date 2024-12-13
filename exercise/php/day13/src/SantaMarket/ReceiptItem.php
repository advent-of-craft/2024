<?php

namespace SantaMarket;

class ReceiptItem
{
    private Product $product;
    private float $price;
    private float $totalPrice;
    private float $quantity;

    public function __construct(Product $product, float $quantity, float $price, float $totalPrice)
    {
        $this->product = $product;
        $this->quantity = $quantity;
        $this->price = $price;
        $this->totalPrice = $totalPrice;
    }

    public function equals(ReceiptItem $other, float $tolerance = 0.001): bool
    {
        return $this->product->equals($other->getProduct()) &&
            $this->isCloseEnough($this->price, $other->getPrice(), $tolerance) &&
            $this->isCloseEnough($this->totalPrice, $other->getTotalPrice(), $tolerance) &&
            $this->isCloseEnough($this->quantity, $other->getQuantity(), $tolerance);
    }

    public function getProduct(): Product
    {
        return $this->product;
    }

    private function isCloseEnough(float $a, float $b, float $tolerance): bool
    {
        return abs($a - $b) <= $tolerance;
    }

    public function getPrice(): float
    {
        return $this->price;
    }

    public function getTotalPrice(): float
    {
        return $this->totalPrice;
    }

    public function getQuantity(): float
    {
        return $this->quantity;
    }
}
