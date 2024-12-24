<?php

namespace Delivery\UseCases;

class DeliverToy
{
    private string $childName;
    private string $desiredToy;

    public function __construct(string $childName, string $desiredToy)
    {
        $this->childName = $childName;
        $this->desiredToy = $desiredToy;
    }

    public function getChildName(): string
    {
        return $this->childName;
    }

    public function getDesiredToy(): string
    {
        return $this->desiredToy;
    }
}
