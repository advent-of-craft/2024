<?php

use SantaMarket\ChristmasElf;
use SantaMarket\Discount;
use SantaMarket\Product;
use SantaMarket\ProductUnit;
use SantaMarket\ReceiptItem;
use SantaMarket\ShoppingSleigh;
use SantaMarket\SpecialOfferType;
use Tests\FakeCatalog;

function expectFloatCloseTo($actual, $expected, $tolerance = 0.001): void
{
    expect(abs($actual - $expected))->toBeLessThanOrEqual($tolerance);
}

function expectDiscountCloseTo(Discount $actual, Discount $expected, $tolerance = 0.001): void
{
    expect($actual->getDescription())->toEqual($expected->getDescription())
        ->and(abs($actual->getDiscountAmount() - $expected->getDiscountAmount()))->toBeLessThanOrEqual($tolerance)
        ->and($actual->getProduct())->toEqual($expected->getProduct());
}

it('calculates total without discounts', function () {
    $catalog = new FakeCatalog();
    $teddyBear = new Product('teddyBear', ProductUnit::EACH);
    $teddyBearPrice = 1.0;
    $catalog->addProduct($teddyBear, $teddyBearPrice);

    $sleigh = new ShoppingSleigh();
    $numberOfTeddyBears = 3;
    $sleigh->addItemQuantity($teddyBear, $numberOfTeddyBears);

    $elf = new ChristmasElf($catalog);
    $receipt = $elf->checksOutArticlesFrom($sleigh);

    $expectedTotalPrice = $numberOfTeddyBears * $teddyBearPrice;
    $expectedReceiptItem = new ReceiptItem($teddyBear, $numberOfTeddyBears, $teddyBearPrice, $expectedTotalPrice);

    expectFloatCloseTo($receipt->getTotalPrice(), $expectedTotalPrice);
    expect($receipt->getItems()[0])->toEqual($expectedReceiptItem);
});

it('applies ten percent discount', function () {
    $catalog = new FakeCatalog();
    $turkey = new Product('turkey', ProductUnit::KILO);
    $turkeyPrice = 2.0;
    $catalog->addProduct($turkey, $turkeyPrice);

    $sleigh = new ShoppingSleigh();
    $turkeyQuantity = 2.0;
    $sleigh->addItemQuantity($turkey, $turkeyQuantity);

    $elf = new ChristmasElf($catalog);
    $elf->addSpecialOffer(SpecialOfferType::TEN_PERCENT_DISCOUNT, $turkey, 10.0);
    $receipt = $elf->checksOutArticlesFrom($sleigh);

    $expectedNonDiscountedPrice = $turkeyQuantity * $turkeyPrice;
    $expectedTotalPrice = $expectedNonDiscountedPrice * 0.9;
    $expectedReceiptItem = new ReceiptItem($turkey, $turkeyQuantity, $turkeyPrice, $expectedNonDiscountedPrice);
    $expectedDiscount = new Discount($turkey, '10% off', round($expectedTotalPrice - $expectedNonDiscountedPrice, 2));

    expectFloatCloseTo($receipt->getTotalPrice(), $expectedTotalPrice);
    expectDiscountCloseTo($receipt->getDiscounts()[0], $expectedDiscount);
    expect($receipt->getItems()[0])->toEqual($expectedReceiptItem);
});

it('applies three for two discount', function () {
    $catalog = new FakeCatalog();
    $teddyBear = new Product('teddyBear', ProductUnit::EACH);
    $teddyBearPrice = 1.0;
    $catalog->addProduct($teddyBear, $teddyBearPrice);

    $elf = new ChristmasElf($catalog);
    $elf->addSpecialOffer(SpecialOfferType::THREE_FOR_TWO, $teddyBear, 0.0);

    $sleigh = new ShoppingSleigh();
    $numberOfTeddyBears = 3;
    $sleigh->addItemQuantity($teddyBear, $numberOfTeddyBears);

    $receipt = $elf->checksOutArticlesFrom($sleigh);

    $expectedNonDiscountedPrice = $numberOfTeddyBears * $teddyBearPrice;
    $expectedTotalPrice = 2 * $teddyBearPrice;
    $expectedReceiptItem = new ReceiptItem($teddyBear, $numberOfTeddyBears, $teddyBearPrice, $expectedNonDiscountedPrice);
    $expectedDiscount = new Discount($teddyBear, '3 for 2', $expectedTotalPrice - $expectedNonDiscountedPrice);

    expectFloatCloseTo($receipt->getTotalPrice(), $expectedTotalPrice);
    expectDiscountCloseTo($receipt->getDiscounts()[0], $expectedDiscount);
    expect($receipt->getItems()[0])->toEqual($expectedReceiptItem);
});

it('applies two for amount discount', function () {
    $catalog = new FakeCatalog();
    $teddyBear = new Product('teddyBear', ProductUnit::EACH);
    $teddyBearPrice = 1.0;
    $catalog->addProduct($teddyBear, $teddyBearPrice);

    $elf = new ChristmasElf($catalog);
    $discountedPriceForTwoTeddyBears = 1.6;
    $elf->addSpecialOffer(SpecialOfferType::TWO_FOR_AMOUNT, $teddyBear, $discountedPriceForTwoTeddyBears);

    $sleigh = new ShoppingSleigh();
    $numberOfTeddyBears = 2;
    $sleigh->addItemQuantity($teddyBear, $numberOfTeddyBears);

    $receipt = $elf->checksOutArticlesFrom($sleigh);

    $expectedNonDiscountedPrice = $numberOfTeddyBears * $teddyBearPrice;
    $expectedTotalPrice = $discountedPriceForTwoTeddyBears;
    $expectedReceiptItem = new ReceiptItem($teddyBear, $numberOfTeddyBears, $teddyBearPrice, $expectedNonDiscountedPrice);
    $expectedDiscount = new Discount($teddyBear, '2 for ' . $discountedPriceForTwoTeddyBears, $expectedTotalPrice - $expectedNonDiscountedPrice);

    expectFloatCloseTo($receipt->getTotalPrice(), $expectedTotalPrice);
    expectDiscountCloseTo($receipt->getDiscounts()[0], $expectedDiscount);
    expect($receipt->getItems()[0])->toEqual($expectedReceiptItem);
});

it('applies five for amount discount', function () {
    $catalog = new FakeCatalog();
    $teddyBear = new Product('teddyBear', ProductUnit::EACH);
    $teddyBearPrice = 1.0;
    $catalog->addProduct($teddyBear, $teddyBearPrice);

    $elf = new ChristmasElf($catalog);
    $discountedPriceForFiveTeddyBears = 4.0;
    $elf->addSpecialOffer(SpecialOfferType::FIVE_FOR_AMOUNT, $teddyBear, $discountedPriceForFiveTeddyBears);

    $sleigh = new ShoppingSleigh();
    $numberOfTeddyBears = 6;
    for ($i = 0; $i < $numberOfTeddyBears; $i++) {
        $sleigh->addItem($teddyBear);
    }

    $receipt = $elf->checksOutArticlesFrom($sleigh);

    $expectedNonDiscountedPrice = $numberOfTeddyBears * $teddyBearPrice;
    $expectedTotalPrice = $discountedPriceForFiveTeddyBears + $teddyBearPrice;
    $expectedReceiptItem = new ReceiptItem($teddyBear, 1, $teddyBearPrice, $teddyBearPrice);
    $expectedDiscount = new Discount($teddyBear, '5 for ' . $discountedPriceForFiveTeddyBears, $expectedTotalPrice - $expectedNonDiscountedPrice);

    expectFloatCloseTo($receipt->getTotalPrice(), $expectedTotalPrice);
    expectDiscountCloseTo($receipt->getDiscounts()[0], $expectedDiscount);
    expect($receipt->getItems()[0])->toEqual($expectedReceiptItem);
});

it('applies discount for one of two different items in sleigh', function () {
    $catalog = new FakeCatalog();
    $teddyBear = new Product('teddyBear', ProductUnit::EACH);
    $teddyBearPrice = 1.0;
    $catalog->addProduct($teddyBear, $teddyBearPrice);

    $turkey = new Product('turkey', ProductUnit::KILO);
    $turkeyPrice = 2.0;
    $catalog->addProduct($turkey, $turkeyPrice);

    $elf = new ChristmasElf($catalog);
    $elf->addSpecialOffer(SpecialOfferType::TEN_PERCENT_DISCOUNT, $teddyBear, 10.0);

    $sleigh = new ShoppingSleigh();
    $sleigh->addItemQuantity($teddyBear, 3);
    $sleigh->addItemQuantity($turkey, 1.5);

    $receipt = $elf->checksOutArticlesFrom($sleigh);

    $expectedNonDiscountedTeddyBearPrice = 3 * $teddyBearPrice;
    $expectedTotalTeddyBearPrice = $expectedNonDiscountedTeddyBearPrice * 0.9;
    $totalTurkeyPrice = $turkeyPrice * 1.5;
    $expectedTotalPrice = $expectedTotalTeddyBearPrice + $totalTurkeyPrice;

    $expectedDiscount = new Discount($teddyBear, '10% off', $expectedTotalTeddyBearPrice - $expectedNonDiscountedTeddyBearPrice);

    expectFloatCloseTo($receipt->getTotalPrice(), $expectedTotalPrice);
    expectDiscountCloseTo($receipt->getDiscounts()[0], $expectedDiscount);
});