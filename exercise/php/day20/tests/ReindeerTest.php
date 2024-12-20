<?php

use GuzzleHttp\Client;
use GuzzleHttp\Exception\ClientException;
use Ramsey\Uuid\Uuid;

beforeAll(function () {
    global $serverProcess;
    $serverProcess = proc_open(
        'php -S localhost:8000 -t src',
        [
            1 => ['pipe', 'w'],
            2 => ['pipe', 'w'],
        ],
        $pipes
    );
    sleep(1);
});

afterAll(function () {
    global $serverProcess;
    proc_terminate($serverProcess);
});

beforeEach(function () {
    $this->client = new Client(['base_uri' => 'http://localhost:8000']);
});

test('should get reindeer', function () {
    $response = $this->client->get('/reindeer/40f9d24d-d3e0-4596-adc5-b4936ff84b19');
    expect($response->getStatusCode())->toBe(200);
});

test('not found for not existing reindeer', function () {
    $nonExistingReindeer = Uuid::uuid4()->toString();

    try {
        $this->client->get("/reindeer/{$nonExistingReindeer}");
    } catch (ClientException $e) {
        $this->assertEquals(404, $e->getResponse()->getStatusCode());
        $this->assertStringContainsString('Reindeer not found', $e->getResponse()->getBody()->getContents());
    }
});


test('conflict when trying to create existing one', function () {
    $request = [
        'json' => [
            'name' => 'Petar',
            'color' => 2 // Purple
        ]
    ];

    try {
        $this->client->post('/reindeer', $request);
    } catch (ClientException $e) {
        $this->assertEquals(409, $e->getResponse()->getStatusCode());
        $this->assertStringContainsString('Reindeer already exists', $e->getResponse()->getBody()->getContents());
    }
});
