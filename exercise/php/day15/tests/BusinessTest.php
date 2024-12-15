<?php

use SantaChristmasList\Business;
use SantaChristmasList\Dependencies\Factory;
use SantaChristmasList\Dependencies\Inventory;
use SantaChristmasList\Dependencies\WishList;
use SantaChristmasList\Models\Child;
use SantaChristmasList\Models\Gift;
use SantaChristmasList\Models\ManufacturedGift;

it('should load a gift into the sleigh', function () {
    $factory = new Factory();
    $inventory = new Inventory();
    $wishList = new WishList();

    $john = new Child('John');
    $toy = new Gift('Toy');
    $manufacturedGift = new ManufacturedGift('123');

    $wishList->set($john, $toy);
    $factory->set($toy, $manufacturedGift);
    $inventory->set('123', $toy);

    $business = new Business($factory, $inventory, $wishList);
    $sleigh = $business->loadGiftsInSleigh($john);

    expect($sleigh->offsetGet($john->getName()))->toBe('Gift: Toy has been loaded!');
});

it('should not load a gift if the child is not on the wish list', function () {
    $factory = new Factory();
    $inventory = new Inventory();
    $wishList = new WishList();

    $john = new Child('John');

    $business = new Business($factory, $inventory, $wishList);
    $sleigh = $business->loadGiftsInSleigh($john);

    expect($sleigh->offsetExists($john->getName()))->toBeFalse();
});

it('should not load a gift if the toy was not manufactured', function () {
    $factory = new Factory();
    $inventory = new Inventory();
    $wishList = new WishList();

    $john = new Child('John');
    $toy = new Gift('Toy');

    $wishList->set($john, $toy);

    $business = new Business($factory, $inventory, $wishList);
    $sleigh = $business->loadGiftsInSleigh($john);

    expect($sleigh->offsetExists($john->getName()))->toBeFalse();
});

it('should not load a gift if the toy was misplaced', function () {
    $factory = new Factory();
    $inventory = new Inventory();
    $wishList = new WishList();

    $john = new Child('John');
    $toy = new Gift('Toy');
    $manufacturedGift = new ManufacturedGift('123');

    $wishList->set($john, $toy);
    $factory->set($toy, $manufacturedGift);

    $business = new Business($factory, $inventory, $wishList);
    $sleigh = $business->loadGiftsInSleigh($john);

    expect($sleigh->offsetExists($john->getName()))->toBeFalse();
});
