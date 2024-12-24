<?php

namespace Delivery\Domain;

use DateTime;
use Delivery\Domain\Core\Event;

class StockReducedEvent extends Event
{
    private string $toyName;
    private StockUnit $newStock;

    public function __construct(string $id, DateTime $date, string $toyName, StockUnit $newStock)
    {
        parent::__construct($id, 1, $date);
        $this->toyName = $toyName;
        $this->newStock = $newStock;
    }

    public function getToyName(): string
    {
        return $this->toyName;
    }

    public function getNewStock(): StockUnit
    {
        return $this->newStock;
    }
}
