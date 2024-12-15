<?php

namespace SantaChristmasList\Models;

class ManufacturedGift {
    private string $barCode;

    public function __construct(string $barCode) {
        $this->barCode = $barCode;
    }

    public function getBarCode(): string {
        return $this->barCode;
    }
}