<?php

use System\Elf;
use System\TaskAssignment;

beforeEach(function () {
    $this->elves = [
        new Elf(1, 5),
        new Elf(2, 10),
        new Elf(3, 20),
    ];
    $this->system = new TaskAssignment($this->elves);
});

test('reportTaskCompletion increases total tasks completed', function () {
    expect($this->system->reportTaskCompletion(1))->toBeTrue()
        ->and($this->system->getTotalTasksCompleted())->toBe(1);
});

test('getElfWithHighestSkill returns the correct elf', function () {
    $highestSkillElf = $this->system->getElfWithHighestSkill();

    expect($highestSkillElf->id)->toBe(3);
});

test('assignTask assigns an elf based on skill level', function () {
    $elf = $this->system->assignTask(8);

    expect($elf)->not()->toBeNull()
        ->and($elf->id)->toBe(2);
});

test('increaseSkillLevel updates elf skill correctly', function () {
    $this->system->increaseSkillLevel(1, 3);

    $elf = $this->system->assignTask(7);

    expect($elf)->not()->toBeNull()
        ->and($elf->id)->toBe(1);
});

test('decreaseSkillLevel updates elf skill correctly', function () {
    $this->system->decreaseSkillLevel(1, 3);
    $this->system->decreaseSkillLevel(2, 5);

    $elf = $this->system->assignTask(4);

    expect($elf)->not()->toBeNull()
        ->and($elf->id)->toBe(2)
        ->and($elf->skillLevel)->toBe(5);
});

test('reassignTask changes assignment correctly', function () {
    $this->system->reassignTask(3, 1);

    $elf = $this->system->assignTask(19);

    expect($elf)->not()->toBeNull()
        ->and($elf->id)->toBe(1);
});

test('assignTask fails when skills required is too high', function () {
    $elf = $this->system->assignTask(50);

    expect($elf)->toBeNull();
});

test('listElvesBySkillDescending returns elves in correct order', function () {
    $sortedElves = $this->system->listElvesBySkillDescending();
    $elfIds = array_map(fn($elf) => $elf->id, $sortedElves);

    expect($elfIds)->toEqual([3, 2, 1]);
});

test('resetAllSkillsToBaseline resets all elves skills to a specified baseline', function () {
    $this->system->resetAllSkillsToBaseline(10);

    $elves = $this->system->listElvesBySkillDescending();

    foreach ($elves as $elf) {
        expect($elf->skillLevel)->toBe(10);
    }
});

test('decreaseSkillLevel updates elf skill and do not allow negative values', function () {
    $this->system->decreaseSkillLevel(1, 10);

    $elf = $this->system->assignTask(4);

    expect($elf)->not()->toBeNull()
        ->and($elf->id)->toBe(1)
        ->and($elf->skillLevel)->toBe(5);
});