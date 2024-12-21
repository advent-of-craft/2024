<?php

namespace Tour;

class Step
{
    public $time;
    public $label;
    public $deliveryTime;

    public function __construct(string $time, string $label, int $deliveryTime)
    {
        $this->time = $time;
        $this->label = $label;
        $this->deliveryTime = $deliveryTime;
    }
}