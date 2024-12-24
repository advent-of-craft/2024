<?php

namespace Delivery\Domain\Core;

class RegisteredRoutes
{
    private array $routes = [];

    public function dispatch(Event $event): void
    {
        $eventType = get_class($event);
        if (isset($this->routes[$eventType])) {
            $this->routes[$eventType]($event);
        }
    }

    public function register(string $eventType, callable $apply): void
    {
        $this->routes[$eventType] = $apply;
    }
}