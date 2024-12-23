<?php

namespace Core\Control;

use External\Deer\Reindeer;

class ReindeerPowerUnit
{
    private Reindeer $reindeer;
    private MagicPowerAmplifier $amplifier;

    public function __construct(Reindeer $reindeer)
    {
        $this->reindeer = $reindeer;
        $this->amplifier = new MagicPowerAmplifier(AmplifierType::BASIC);
    }

    public function harnessMagicPower(): float
    {
        if (!$this->reindeer->needsRest()) {
            $this->reindeer->increaseHarnessingTimes();
            return $this->amplifier->amplify($this->reindeer->getMagicPower());
        }

        return 0;
    }

    public function getReindeer(): Reindeer
    {
        return $this->reindeer;
    }
}
