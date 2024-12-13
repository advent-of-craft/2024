<?php

namespace SantaMarket;

class ShoppingSleigh
{
    private array $items = [];
    private array $productQuantities = [];

    public function getItems(): array
    {
        return $this->items;
    }

    public function addItem(Product $product): void
    {
        $this->addItemQuantity($product, 1.0);
    }

    public function addItemQuantity(Product $product, float $quantity): void
    {
        $this->items[] = new ProductQuantity($product, $quantity);
        if (array_key_exists($product->getName(), $this->productQuantities)) {
            $this->productQuantities[$product->getName()] += $quantity;
        } else {
            $this->productQuantities[$product->getName()] = $quantity;
        }
    }

    public function handleOffers(Receipt $receipt, array $offers, SantamarketCatalog $catalog): void
    {
        foreach ($this->productQuantities() as $productName => $quantity) {
            $product = $this->findProductByName($productName);
            if ($product && isset($offers[$productName])) {
                $offer = $offers[$productName];
                $unitPrice = $catalog->getUnitPrice($product);
                $quantityAsInt = (int)$quantity;
                $discount = null;
                $x = 1;

                switch ($offer->offerType) {
                    case SpecialOfferType::THREE_FOR_TWO:
                        $x = 3;
                        if ($quantityAsInt >= 3) {
                            $discountAmount = $quantity * $unitPrice - ($quantityAsInt / $x * 2 * $unitPrice + $quantityAsInt % $x * $unitPrice);
                            $discount = new Discount($product, "3 for 2", -$discountAmount);
                        }
                        break;

                    case SpecialOfferType::TWO_FOR_AMOUNT:
                        $x = 2;
                        if ($quantityAsInt >= 2) {
                            $total = $offer->argument * (floor($quantityAsInt / $x)) + ($quantityAsInt % $x) * $unitPrice;
                            $discountAmount = $unitPrice * $quantity - $total;
                            $discount = new Discount($product, "2 for " . $offer->argument, -$discountAmount);
                        }
                        break;

                    case SpecialOfferType::FIVE_FOR_AMOUNT:
                        $x = 5;
                        if ($quantityAsInt >= 5) {
                            $discountTotal = $unitPrice * $quantity - ($offer->argument * floor($quantityAsInt / $x) + $quantityAsInt % $x * $unitPrice);
                            $discount = new Discount($product, "$x for " . $offer->argument, -$discountTotal);
                        }
                        break;

                    case SpecialOfferType::TEN_PERCENT_DISCOUNT:
                        $discountAmount = -$quantity * $unitPrice * ($offer->argument / 100.0);
                        $discount = new Discount($product, $offer->argument . "% off", $discountAmount);
                        break;
                }

                if ($discount !== null) {
                    $receipt->addDiscount($discount);
                }
            }
        }
    }

    public function productQuantities(): array
    {
        return $this->productQuantities;
    }

    private function findProductByName(string $productName): ?Product
    {
        foreach ($this->items as $item) {
            if ($item->getProduct()->getName() === $productName) {
                return $item->getProduct();
            }
        }
        return null;
    }
}