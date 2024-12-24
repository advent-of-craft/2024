<?php

namespace Tests\Unit;

use Delivery\Domain\Toy;
use QuickCheck\Generator;
use QuickCheck\PHPUnit\PropertyConstraint;
use QuickCheck\Property;
use Tests\Time;

it('can not create toy with invalid stock', closure: function () {
    $this->assertThat(Property::forAll(
        [Generator::choose(1, 1_000_000_000)->map(fn($x) => -$x)],
        function ($stock) {
            try {
                Toy::create(Time::$PROVIDER, 'A toy', $stock);
                throw new \Exception("Should not succeed");
            } catch (\Exception $e) {
                return $e->getMessage() === "A stock unit cannot be negative";
            }
        }
    ), PropertyConstraint::check(1000));
});

it('can create toy with valid stock', function () {
    $this->assertThat(Property::forAll(
        [Generator::choose(0, 1_000_000_000)],
        function ($stock) {
            return Toy::create(Time::$PROVIDER, 'Another toy', $stock) instanceof Toy;
        }
    ), PropertyConstraint::check(1000));
});