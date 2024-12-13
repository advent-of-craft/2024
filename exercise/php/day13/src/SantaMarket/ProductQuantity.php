<?php

namespace SantaMarket;

class ProductQuantity
{
    private Product $product;
    private float $quantity;

    public function __construct(Product $product, float $quantity)
    {
        $this->product = $product;
        $this->quantity = $quantity;
    }

    public function getProduct(): Product
    {
        return $this->product;
    }

    public function getQuantity(): float
    {
        return $this->quantity;
    }
}
