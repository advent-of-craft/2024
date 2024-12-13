<?php

namespace SantaMarket;

interface SantamarketCatalog
{
    public function addProduct(Product $product, float $price): void;

    public function getUnitPrice(Product $product): float;
}