<?php

namespace Core\Control;

class MagicPowerAmplifier
{
    private AmplifierType $amplifierType;

    public function __construct(AmplifierType $amplifierType)
    {
        $this->amplifierType = $amplifierType;
    }

    public function amplify(float $magicPower): float
    {
        return $magicPower > 0 ? $magicPower * $this->amplifierType->getMultiplier() : $magicPower;
    }
}
