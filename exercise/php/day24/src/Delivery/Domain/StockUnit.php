<?php

namespace Delivery\Domain;

class StockUnit
{
    private int $value;

    private function __construct(int $value)
    {
        $this->value = $value;
    }

    /**
     * @throws \Exception
     */
    public static function from(int $stock): StockUnit
    {
        return $stock >= 0
            ? new self($stock)
            : throw new \Exception('A stock unit cannot be negative');
    }

    public function isSupplied(): bool
    {
        return $this->value > 0;
    }

    public function decrease(): self
    {
        return new self($this->value - 1);
    }

    public function getValue(): int
    {
        return $this->value;
    }
}
