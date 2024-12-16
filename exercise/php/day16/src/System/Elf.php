<?php

namespace System;

class Elf {
    public int $id;
    public int $skillLevel;

    public function __construct(int $id, int $skillLevel) {
        $this->id = $id;
        $this->skillLevel = $skillLevel;
    }
}