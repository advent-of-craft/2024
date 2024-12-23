<?php

namespace External\Deer;

class Reindeer
{
    private int $spirit;
    private int $age;
    private string $name;
    private bool $sick;
    private int $timesHarnessing = 0;
    private int $powerPullLimit;

    public function __construct(string $name, int $age, int $spirit, bool $sick = false)
    {
        $this->name = $name;
        $this->spirit = $spirit;
        $this->age = $age;
        $this->sick = $sick;

        $this->powerPullLimit = $age <= 5 ? 5 : 5 - ($age - 5);
    }

    public function getMagicPower(): float
    {
        if (!$this->sick || $this->needsRest()) {
            if ($this->age == 1) {
                return $this->spirit * 0.5;
            } elseif ($this->age <= 5) {
                return $this->spirit;
            } else {
                return $this->spirit * 0.25;
            }
        } else {
            return 0;
        }
    }

    public function needsRest(): bool
    {
        if (!$this->sick) {
            return $this->timesHarnessing == $this->powerPullLimit;
        } else {
            return true;
        }
    }

    public function increaseHarnessingTimes(): void
    {
        $this->timesHarnessing++;
    }

    public function resetHarnessingTimes(): void
    {
        $this->timesHarnessing = 0;
    }
}
