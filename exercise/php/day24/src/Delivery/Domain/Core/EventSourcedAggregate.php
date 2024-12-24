<?php

namespace Delivery\Domain\Core;

use DateTime;

abstract class EventSourcedAggregate implements IAggregate
{
    protected string $id;
    private array $uncommittedEvents = [];
    private RegisteredRoutes $registeredRoutes;
    private $timeProvider;
    private int $version = 0;

    protected function __construct(callable $timeProvider)
    {
        $this->timeProvider = $timeProvider;
        $this->registeredRoutes = new RegisteredRoutes();
        $this->registerRoutes();
    }

    protected abstract function registerRoutes(): void;

    public function getId(): string
    {
        return $this->id;
    }

    public function getVersion(): int
    {
        return $this->version;
    }

    public function getUncommittedEvents(): array
    {
        return $this->uncommittedEvents;
    }

    public function clearUncommittedEvents(): void
    {
        $this->uncommittedEvents = [];
    }

    public function time(): DateTime
    {
        return call_user_func($this->timeProvider);
    }

    protected function registerEventRoute(string $eventType, callable $apply): void
    {
        $this->registeredRoutes->register($eventType, $apply);
    }

    protected function raiseEvent(Event $event): void
    {
        $this->applyEvent($event);
        $this->uncommittedEvents[] = $event;
    }

    public function applyEvent(Event $event): void
    {
        $this->registeredRoutes->dispatch($event);
        $this->version++;
    }
}