<?php

use Eid\EID;
use Eid\SerialNumber;
use Eid\Sex;
use Eid\Year;
use Monad\Option\None;
use QuickCheck\Generator;
use QuickCheck\PHPUnit\PropertyConstraint;
use QuickCheck\Property;
use Tests\Mutator;

function validEIDGenerator(): Generator
{
    $sexGenerator = Generator::elements(Sex::cases());
    $yearGenerator = Generator::choose(0, 99)->map(fn($x) => Year::parse($x)->value());
    $serialNumberGenerator = Generator::choose(1, 999)->map(fn($x) => SerialNumber::parse($x)->value());

    return Generator::tuples($sexGenerator, $yearGenerator, $serialNumberGenerator)
        ->map(fn($tuple) => new EID($tuple[0], $tuple[1], $tuple[2]));
}

it('valid EID round tripping', function () {
    $this->assertThat(Property::forAll(
        [validEIDGenerator()],
        function ($validEid) {
            return EID::parse($validEid->__toString())->value() == $validEid;
        }
    ), PropertyConstraint::check(1000));
});

it('invalid EID can never be parsed', function () {
    $this->assertThat(Property::forAll(
        [validEIDGenerator(), mutantGenerator()],
        function ($validEid, $mutator) {
            return (EID::parse($mutator->mutate($validEid)) instanceof None);
        }
    ), PropertyConstraint::check(1000));
});

function mutantGenerator(): Generator
{
    $aMutator = new Mutator("a mutator", function ($eid) {
        return Generator::elements("Implement this first mutator");
    });
    return Generator::elements([$aMutator]);
}