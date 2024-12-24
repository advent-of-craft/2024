<?php

namespace Delivery\Domain\Core;

use DateTime;

class Event
{
    private string $id;
    private int $version;
    private DateTime $date;

    public function __construct(string $id, int $version, DateTime $date)
    {
        $this->id = $id;
        $this->version = $version;
        $this->date = $date;
    }

    public function getId(): string
    {
        return $this->id;
    }

    public function getVersion(): int
    {
        return $this->version;
    }

    public function getDate(): DateTime
    {
        return $this->date;
    }
}