<?php

use Core\Control\ControlSystem;
use Core\Control\SleighAction;
use Core\Control\SleighEngineStatus;

beforeEach(function () {
    $this->outputStreamCaptor = fopen('php://output', 'w');
    ob_start();
});

afterEach(function () {
    fclose($this->outputStreamCaptor);
    ob_end_clean();
});

it('should start the system', function () {
    // The system has been started
    $controlSystem = new ControlSystem();
    $controlSystem->action = SleighAction::FLYING;
    $controlSystem->status = SleighEngineStatus::OFF;
    $controlSystem->startSystem();
    expect($controlSystem->status)->toBe(SleighEngineStatus::ON);
    expect(ob_get_clean())->toBe("Starting the sleigh...\nSystem ready.\n");
});

it('should ascend the system', function () {
    $controlSystem = new ControlSystem();
    $controlSystem->startSystem();
    $controlSystem->ascend();
    expect($controlSystem->action)->toBe(SleighAction::FLYING);
    expect(ob_get_clean())->toBe("Starting the sleigh...\nSystem ready.\nAscending...\n");
});

it('should descend the system', function () {
    $controlSystem = new ControlSystem();
    $controlSystem->startSystem();
    $controlSystem->ascend();
    $controlSystem->descend();
    expect($controlSystem->action)->toBe(SleighAction::HOVERING);
    expect(ob_get_clean())->toBe("Starting the sleigh...\nSystem ready.\nAscending...\nDescending...\n");
});

it('should park the system', function () {
    $controlSystem = new ControlSystem();
    $controlSystem->startSystem();
    $controlSystem->park();
    expect($controlSystem->action)->toBe(SleighAction::PARKED);
});