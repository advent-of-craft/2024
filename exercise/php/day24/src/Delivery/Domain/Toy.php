<?php

namespace Delivery\Domain;

use Delivery\Domain\Core\EventSourcedAggregate;
use Ramsey\Uuid\Uuid;

class Toy extends EventSourcedAggregate
{
    private ?string $name;
    private StockUnit $stock;

    private function __construct(callable $timeProvider, string $name, StockUnit $stock)
    {
        parent::__construct($timeProvider);
        $this->raiseEvent(new ToyCreatedEvent(Uuid::uuid4(), $timeProvider(), $name, $stock));
    }

    /**
     * @throws \Exception
     */
    public static function create(callable $timeProvider, string $name, int $stock): Toy
    {
        return new self(
            $timeProvider,
            $name,
            StockUnit::from($stock)
        );
    }

    /**
     * @throws \Exception
     */
    public function reduceStock(): Toy
    {
        if (!$this->stock->isSupplied()) {
            throw new \Exception("No more " . $this->name . " in stock");
        }
        $this->raiseEvent(new StockReducedEvent($this->id, $this->time(), $this->name, $this->stock->decrease()));
        return $this;
    }

    protected function registerRoutes(): void
    {
        $this->registerEventRoute(ToyCreatedEvent::class, function ($evt) {
            $this->applyToyCreatedEvent($evt);
        });
        $this->registerEventRoute(StockReducedEvent::class, function ($evt) {
            $this->applyStockReducedEvent($evt);
        });
    }

    private function applyToyCreatedEvent(ToyCreatedEvent $event): void
    {
        $this->id = $event->getId();
        $this->name = $event->getName();
        $this->stock = $event->getStock();
    }

    public function getName(): ?string
    {
        return $this->name;
    }

    private function applyStockReducedEvent(StockReducedEvent $event): void
    {
        $this->stock = $event->getNewStock();
    }
}
