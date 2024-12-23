<?php

namespace Core\Control;

enum AmplifierType: int
{
    case BASIC = 1;
    case BLESSED = 2;
    case DIVINE = 3;

    public function getMultiplier(): int
    {
        return $this->value;
    }
}
