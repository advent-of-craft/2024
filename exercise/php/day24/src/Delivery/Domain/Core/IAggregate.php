<?php

namespace Delivery\Domain\Core;

interface IAggregate
{
    public function getId(): string;

    public function getVersion(): int;

    public function applyEvent(Event $event): void;

    public function getUncommittedEvents(): array;

    public function clearUncommittedEvents(): void;
}