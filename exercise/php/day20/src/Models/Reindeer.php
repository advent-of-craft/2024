<?php

namespace Reindeer\Models;

use Reindeer\Enums\ReindeerColor;

class Reindeer {
    public string $id;
    public string $name;
    public ReindeerColor $color;

    public function __construct(string $id, string $name, ReindeerColor $color) {
        $this->id = $id;
        $this->name = $name;
        $this->color = $color;
    }
}
