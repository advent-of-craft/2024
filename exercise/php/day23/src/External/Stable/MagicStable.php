<?php

namespace External\Stable;

use External\Deer\Reindeer;

class MagicStable
{
    private Reindeer $dasher;
    private Reindeer $dancer;
    private Reindeer $prancer;
    private Reindeer $vixen;
    private Reindeer $comet;
    private Reindeer $cupid;
    private Reindeer $donner;
    private Reindeer $blitzen;
    private Reindeer $rudolph;

    public function __construct()
    {
        $this->dasher = new Reindeer("Dasher", 4, 10, true);
        $this->dancer = new Reindeer("Dancer", 2, 8);
        $this->prancer = new Reindeer("Prancer", 3, 9);
        $this->vixen = new Reindeer("Vixen", 3, 6);
        $this->comet = new Reindeer("Comet", 4, 9, true);
        $this->cupid = new Reindeer("Cupid", 4, 6);
        $this->donner = new Reindeer("Donner", 7, 6);
        $this->blitzen = new Reindeer("Blitzen", 8, 7);
        $this->rudolph = new Reindeer("Rudolph", 6, 3);
    }

    public function getAllReindeers(): array
    {
        return [
            $this->dasher,
            $this->dancer,
            $this->prancer,
            $this->vixen,
            $this->comet,
            $this->cupid,
            $this->donner,
            $this->blitzen,
            $this->rudolph
        ];
    }
}
