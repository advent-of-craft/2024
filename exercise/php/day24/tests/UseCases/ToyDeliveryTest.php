<?php

namespace Delivery\Tests\UseCases;

use Delivery\Domain\Core\Event;
use Delivery\Domain\StockReducedEvent;
use Delivery\Domain\StockUnit;
use Delivery\Domain\Toy;
use Delivery\UseCases\DeliverToy;
use Delivery\UseCases\ToyDeliveryUseCase;
use PHPUnit\Framework\TestCase;
use Tests\Doubles\InMemoryToyRepository;
use Tests\Time;

beforeEach(function () {
    $this->toyRepository = new InMemoryToyRepository();
    $this->useCase = new ToyDeliveryUseCase($this->toyRepository);
});

/**
 * @param TestCase $this
 * @return Toy
 * @throws \Exception
 */
function forASuppliedToy(TestCase $context, int $stock): Toy
{
    $toy = Toy::create(Time::$PROVIDER, 'sup', $stock);
    $context->toyRepository->save($toy);
    return $toy;
}

test('successfully deliver toy and update stock', function () {
    $toy = forASuppliedToy($this, 1);
    $command = new DeliverToy('bobby', $toy->getName());

    $this->useCase->handle($command);

    expect($toy->getVersion())->toBe(2);
    expectRaisedEvent($this->toyRepository,
        new StockReducedEvent(
            $toy->getId(),
            Time::$NOW,
            $command->getDesiredToy(),
            StockUnit::from(0)
        )
    );
});

test('fail when toy has not been built', function () {
    $notBuiltToy = 'non_existent_toy';
    $command = new DeliverToy('bobby', $notBuiltToy);

    expect(function () use ($command) {
        $this->useCase->handle($command);
    })->toThrow(\Exception::class, "Oops we have a problem... we have not built the toy: {$notBuiltToy}");
});

test('fail when toy is not supplied anymore', function () {
    $toy = forASuppliedToy($this, 0);
    $command = new DeliverToy('tommy', $toy->getName());

    expect(function () use ($command) {
        $this->useCase->handle($command);
    })->toThrow(\Exception::class, "No more {$toy->getName()} in stock");
});

function expectRaisedEvent(InMemoryToyRepository $repository, Event $expectedEvent): void
{
    $raisedEvents = $repository->raisedEvents();
    $lastEvent = end($raisedEvents);

    expect($lastEvent == $expectedEvent)->toBeTrue();
}