<?php

use Gifts\Child;
use Gifts\Santa;
use Gifts\Toy;

beforeEach(function () {
    $this->playstation = new Toy("playstation");
    $this->ball = new Toy("ball");
    $this->plush = new Toy("plush");
    $this->santa = new Santa();
});

it('gives the third choice toy to a naughty child', function () {
    $bobby = new Child("bobby", "naughty");
    $bobby->setWishlist($this->playstation, $this->plush, $this->ball);
    $this->santa->addChild($bobby);

    $chosenToy = $this->santa->chooseToyForChild("bobby");
    expect($chosenToy->equals($this->ball))->toBeTrue();
});

it('gives the second choice toy to a nice child', function () {
    $bobby = new Child("bobby", "nice");
    $bobby->setWishlist($this->playstation, $this->plush, $this->ball);
    $this->santa->addChild($bobby);

    $chosenToy = $this->santa->chooseToyForChild("bobby");
    expect($chosenToy->equals($this->plush))->toBeTrue();
});

it('gives the first choice toy to a very nice child', function () {
    $bobby = new Child("bobby", "very nice");
    $bobby->setWishlist($this->playstation, $this->plush, $this->ball);
    $this->santa->addChild($bobby);

    $chosenToy = $this->santa->chooseToyForChild("bobby");
    expect($chosenToy->equals($this->playstation))->toBeTrue();
});

it('throws an exception when a non-existing child is queried', function () {
    $bobby = new Child("bobby", "very nice");
    $bobby->setWishlist($this->playstation, $this->plush, $this->ball);
    $this->santa->addChild($bobby);

    $chooseToy = fn() => $this->santa->chooseToyForChild("alice");
    expect($chooseToy)->toThrow(InvalidArgumentException::class, "No such child found");
});