<?php

namespace Reindeer\Controllers;

use Reindeer\Services\ReindeerService;
use Reindeer\Enums\ReindeerColor;
use Reindeer\Enums\ReindeerErrorCode;

class ReindeerController {
    private ReindeerService $service;

    public function __construct(ReindeerService $service) {
        $this->service = $service;
    }

    public function getReindeer(string $id) {
        $result = $this->service->get($id);

        if ($result instanceof ReindeerErrorCode) {
            http_response_code(404);
            echo json_encode(['error' => 'Reindeer not found']);
        } else {
            echo json_encode($result);
        }
    }

    public function createReindeer() {
        $data = json_decode(file_get_contents('php://input'), true);
        $name = $data['name'];
        $color = ReindeerColor::from($data['color']);

        $result = $this->service->create($name, $color);

        if ($result instanceof ReindeerErrorCode) {
            http_response_code(409);
            echo json_encode(['error' => 'Reindeer already exists']);
        } else {
            http_response_code(201);
            echo json_encode($result);
        }
    }
}
