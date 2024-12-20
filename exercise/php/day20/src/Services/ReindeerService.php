<?php

namespace Reindeer\Services;

use Ramsey\Uuid\Uuid;
use Reindeer\Models\Reindeer;
use Reindeer\Enums\ReindeerColor;
use Reindeer\Enums\ReindeerErrorCode;

class ReindeerService {
    private array $reindeer;

    public function __construct() {
        $this->reindeer = [
            new Reindeer('40f9d24d-d3e0-4596-adc5-b4936ff84b19', 'Petar', ReindeerColor::Black)
        ];
    }

    public function get(string $id): Reindeer|ReindeerErrorCode {
        foreach ($this->reindeer as $reindeer) {
            if ($reindeer->id === $id) {
                return $reindeer;
            }
        }
        return ReindeerErrorCode::NotFound;
    }

    public function create(string $name, ReindeerColor $color): Reindeer|ReindeerErrorCode {
        foreach ($this->reindeer as $reindeer) {
            if ($reindeer->name === $name) {
                return ReindeerErrorCode::AlreadyExist;
            }
        }

        $reindeer = new Reindeer(Uuid::uuid4()->toString(), $name, $color);
        $this->reindeer[] = $reindeer;
        return $reindeer;
    }
}
