<?php

namespace Tests;

use SantaMarket\Product;
use SantaMarket\SantamarketCatalog;

class FakeCatalog implements SantamarketCatalog
{
    private array $products = [];
    private array $prices = [];

    public function addProduct(Product $product, float $price): void
    {
        $this->products[$product->getName()] = $product;
        $this->prices[$product->getName()] = $price;
    }

    public function getUnitPrice(Product $product): float
    {
        return $this->prices[$product->getName()];
    }
}