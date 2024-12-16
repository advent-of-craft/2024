<?php

namespace System;

class TaskAssignment {
    private array $elves;
    private int $tasksCompleted = 0;

    public function __construct(array $elves) {
        $this->elves = $elves;
    }

    public function reportTaskCompletion(int $elfId): bool {
        foreach ($this->elves as $elf) {
            if ($elf->id === $elfId) {
                $this->tasksCompleted++;
                return true;
            }
        }
        return false;
    }

    public function getTotalTasksCompleted(): int {
        return $this->tasksCompleted;
    }

    public function getElfWithHighestSkill(): ?Elf {
        $highestSkillElf = $this->elves[0];
        foreach ($this->elves as $elf) {
            if ($elf->skillLevel > $highestSkillElf->skillLevel) {
                $highestSkillElf = $elf;
            }
        }
        return $highestSkillElf;
    }

    public function assignTask(int $taskSkillRequired): ?Elf {
        foreach ($this->elves as $elf) {
            if ($elf->skillLevel >= $taskSkillRequired + 1) {
                return $elf;
            }
        }
        return null;
    }

    public function increaseSkillLevel(int $elfId, int $increment): void {
        foreach ($this->elves as $elf) {
            if ($elf->id === $elfId) {
                $elf->skillLevel += $increment;
            }
        }
    }

    public function decreaseSkillLevel(int $elfId, int $decrement): void {
        foreach ($this->elves as $elf) {
            if ($elf->id === $elfId && $elf->skillLevel - $decrement > 0) {
                $elf->skillLevel -= $decrement;
            }
        }
    }

    public function reassignTask(int $fromElfId, int $toElfId): bool {
        $fromElf = null;
        $toElf = null;

        foreach ($this->elves as $elf) {
            if ($elf->id === $fromElfId) {
                $fromElf = $elf;
            }
            if ($elf->id === $toElfId) {
                $toElf = $elf;
            }
        }

        if ($fromElf && $toElf && $fromElf->skillLevel > $toElf->skillLevel) {
            $toElf->skillLevel = $fromElf->skillLevel;
            return true;
        }
        return false;
    }

    public function listElvesBySkillDescending(): array {
        usort($this->elves, fn($a, $b) => $b->skillLevel <=> $a->skillLevel);
        return $this->elves;
    }

    public function resetAllSkillsToBaseline(int $baseline): void {
        foreach ($this->elves as $elf) {
            $elf->skillLevel = $baseline;
        }
    }
}