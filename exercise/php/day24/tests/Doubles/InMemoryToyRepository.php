<?php

namespace Tests\Doubles;

use Delivery\Domain\IToyRepository;
use Delivery\Domain\Toy;

class InMemoryToyRepository implements IToyRepository
{
    private array $toys = [];
    private array $raisedEvents = [];

    public function findByName(string $toyName): ?Toy
    {
        foreach ($this->toys as $toy) {
            if ($toy->getName() === $toyName) {
                return $toy;
            }
        }
        return null;
    }

    public function findById(string $id): ?Toy
    {
        return $this->toys[$id] ?? null;
    }

    public function save(Toy $toy): void
    {
        $this->raisedEvents = [];
        $this->toys[$toy->getId()] = $toy;

        foreach ($toy->getUncommittedEvents() as $event) {
            $this->raisedEvents[] = $event;
        }

        $toy->clearUncommittedEvents();
    }

    public function raisedEvents(): array
    {
        return $this->raisedEvents;
    }
}
