<?php

require_once __DIR__ . '/../vendor/autoload.php';

use Reindeer\Services\ReindeerService;
use Reindeer\Controllers\ReindeerController;

$service = new ReindeerService();
$controller = new ReindeerController($service);

$uri = parse_url($_SERVER['REQUEST_URI'], PHP_URL_PATH);
$method = $_SERVER['REQUEST_METHOD'];

header('Content-Type: application/json');

if ($uri === '/reindeer' && $method === 'POST') {
    $controller->createReindeer();
} elseif (preg_match('/\/reindeer\/([a-f0-9\-]+)/', $uri, $matches) && $method === 'GET') {
    $controller->getReindeer($matches[1]);
} else {
    http_response_code(404);
    echo json_encode(['error' => 'Not Found']);
}
