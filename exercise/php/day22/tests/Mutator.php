<?php

namespace Tests;

use Eid\EID;

class Mutator
{
    public string $name;
    public $func;

    public function __construct(string $name, callable $func)
    {
        $this->name = $name;
        $this->func = $func;
    }

    public function mutate(EID $eid): string
    {
        $gen = call_user_func($this->func, $eid);
        return $gen->takeSamples()[0];
    }
}