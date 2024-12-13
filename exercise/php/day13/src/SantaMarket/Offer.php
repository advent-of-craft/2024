<?php

namespace SantaMarket;

class Offer
{
    public Product $product;
    public SpecialOfferType $offerType;
    public float $argument;

    public function __construct(SpecialOfferType $offerType, Product $product, float $argument)
    {
        $this->offerType = $offerType;
        $this->product = $product;
        $this->argument = $argument;
    }
}
