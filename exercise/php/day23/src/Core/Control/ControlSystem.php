<?php

namespace Core\Control;

use External\Deer\Reindeer;
use External\Stable\MagicStable;

class ControlSystem
{
    private int $XmasSpirit = 40;
    private Dashboard $dashboard;
    private MagicStable $magicStable;
    private array $reindeerPowerUnits;
    public SleighEngineStatus $status;
    public SleighAction $action;
    private float $controlMagicPower = 0;

    public function __construct()
    {
        $this->dashboard = new Dashboard();
        $this->magicStable = new MagicStable();
        $this->reindeerPowerUnits = $this->bringAllReindeers();
    }

    private function bringAllReindeers(): array
    {
        return array_map([$this, 'attachPowerUnit'], $this->magicStable->getAllReindeers());
    }

    public function attachPowerUnit(Reindeer $reindeer): ReindeerPowerUnit
    {
        return new ReindeerPowerUnit($reindeer);
    }

    public function startSystem(): void
    {
        $this->dashboard->displayStatus("Starting the sleigh...");
        $this->status = SleighEngineStatus::ON;
        $this->dashboard->displayStatus("System ready.");
    }

    /**
     * @throws ReindeersNeedRestException
     * @throws SleighNotStartedException
     */
    public function ascend(): void
    {
        if ($this->status === SleighEngineStatus::ON) {
            foreach ($this->reindeerPowerUnits as $reindeerPowerUnit) {
                $this->controlMagicPower += $reindeerPowerUnit->harnessMagicPower();
            }

            if ($this->checkReindeerStatus()) {
                $this->dashboard->displayStatus("Ascending...");
                $this->action = SleighAction::FLYING;
                $this->controlMagicPower = 0;
            } else {
                throw new ReindeersNeedRestException();
            }
        } else {
            throw new SleighNotStartedException();
        }
    }

    public function descend(): void
    {
        if ($this->status === SleighEngineStatus::ON) {
            $this->dashboard->displayStatus("Descending...");
            $this->action = SleighAction::HOVERING;
        } else {
            throw new SleighNotStartedException();
        }
    }

    public function park(): void
    {
        if ($this->status === SleighEngineStatus::ON) {
            $this->dashboard->displayStatus("Parking...");

            foreach ($this->reindeerPowerUnits as $reindeerPowerUnit) {
                $reindeerPowerUnit->getReindeer()->resetHarnessingTimes();
            }

            $this->action = SleighAction::PARKED;
        } else {
            throw new SleighNotStartedException();
        }
    }

    public function stopSystem(): void
    {
        $this->dashboard->displayStatus("Stopping the sleigh...");
        $this->status = SleighEngineStatus::OFF;
        $this->dashboard->displayStatus("System shutdown.");
    }

    private function checkReindeerStatus(): bool
    {
        return $this->controlMagicPower >= $this->XmasSpirit;
    }
}
