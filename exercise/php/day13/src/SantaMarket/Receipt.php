<?php

namespace SantaMarket;

class Receipt
{
    private array $items = [];
    private array $discounts = [];

    public function addProduct(Product $product, float $quantity, float $price, float $totalPrice): void
    {
        $this->items[] = new ReceiptItem($product, $quantity, $price, $totalPrice);
    }

    public function addDiscount(Discount $discount): void
    {
        $this->discounts[] = $discount;
    }

    public function getTotalPrice(): float
    {
        $total = 0;
        foreach ($this->items as $item) {
            $total += $item->getTotalPrice();
        }
        foreach ($this->discounts as $discount) {
            $total += $discount->getDiscountAmount();
        }
        return $total;
    }

    public function getItems(): array
    {
        return $this->items;
    }

    public function getDiscounts(): array
    {
        return $this->discounts;
    }
}
