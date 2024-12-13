<?php

namespace SantaMarket;

class ChristmasElf
{
    private SantamarketCatalog $catalog;
    private array $offers = [];

    public function __construct(SantamarketCatalog $catalog)
    {
        $this->catalog = $catalog;
    }

    public function addSpecialOffer(SpecialOfferType $offerType, Product $product, float $argument): void
    {
        $this->offers[$product->getName()] = new Offer($offerType, $product, $argument);
    }

    public function checksOutArticlesFrom(ShoppingSleigh $sleigh): Receipt
    {
        $receipt = new Receipt();
        $productQuantities = $sleigh->getItems();

        foreach ($productQuantities as $pq) {
            $product = $pq->getProduct();
            $quantity = $pq->getQuantity();
            $unitPrice = $this->catalog->getUnitPrice($product);
            $price = $quantity * $unitPrice;
            $receipt->addProduct($product, $quantity, $unitPrice, $price);
        }

        $sleigh->handleOffers($receipt, $this->offers, $this->catalog);

        return $receipt;
    }
}