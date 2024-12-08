<?php

namespace Tests\Doubles;

use Domain\Toy;
use Domain\ToyRepository;

class InMemoryToyRepository implements ToyRepository
{
    private array $toys = [];

    public function findByName(string $name): ?Toy
    {
        foreach ($this->toys as $toy) {
            if ($toy->getName() === $name) {
                return $toy;
            }
        }
        return null;
    }

    public function save(Toy $toy): void
    {
        $this->toys = array_filter($this->toys, function ($t) use ($toy) {
            return $t->getName() !== $toy->getName();
        });
        $this->toys[] = $toy;
    }
}
