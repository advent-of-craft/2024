<?php

namespace Delivery\Domain;

use DateTime;
use Delivery\Domain\Core\Event;

class ToyCreatedEvent extends Event
{
    private string $name;
    private StockUnit $stock;

    public function __construct(string $id, DateTime $date, string $name, StockUnit $stock)
    {
        parent::__construct($id, 1, $date);
        $this->name = $name;
        $this->stock = $stock;
    }

    public function getName(): string
    {
        return $this->name;
    }

    public function getStock(): StockUnit
    {
        return $this->stock;
    }
}
