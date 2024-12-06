<?php

declare(strict_types = 1);

use Workshop\ElfWorkshop;

beforeEach(function (): void {
	$this->workshop = new ElfWorkshop;
});

test('addTask should add a task', function (): void {
	$this->workshop->addTask('Build toy train');
	expect($this->workshop->getTaskList())->toContain('Build toy train');
});

test('addTask should add Craft dollhouse task', function (): void {
	$this->workshop->addTask('Craft dollhouse');
	expect($this->workshop->getTaskList())->toContain('Craft dollhouse');
});

test('addTask should add Paint bicycle task', function (): void {
	$this->workshop->addTask('Paint bicycle');
	expect($this->workshop->getTaskList())->toContain('Paint bicycle');
});

test('addTask should handle empty tasks correctly', function (): void {
	$this->workshop->addTask('');
	expect($this->workshop->getTaskList())->toBeEmpty();
});

test('addTask should handle null tasks correctly', function (): void {
	$this->workshop->addTask(null);
	expect($this->workshop->getTaskList())->toBeEmpty();
});

test('completeTask should remove task', function (): void {
	$this->workshop->addTask('Wrap gifts');
	$removedTask = $this->workshop->completeTask();
	expect($removedTask)->toBe('Wrap gifts');
	expect($this->workshop->getTaskList())->toBeEmpty();
});
