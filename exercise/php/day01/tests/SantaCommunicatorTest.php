<?php

declare(strict_types = 1);

use Communication\SantaCommunicator;
use Tests\doubles\TestLogger;

const NORTH_POLE = 'North Pole';
const DASHER = 'Dasher';
const NUMBER_OF_DAYS_TO_REST = 2;
const NUMBER_OF_DAYS_BEFORE_CHRISTMAS = 24;

beforeEach(function (): void {
	$this->logger = new TestLogger;
	$this->communicator = new SantaCommunicator(NUMBER_OF_DAYS_TO_REST);
});

it('composes a message', function (): void {
	$communicator = new SantaCommunicator(NUMBER_OF_DAYS_TO_REST);
	$message = $communicator->composeMessage(DASHER, NORTH_POLE, 5, NUMBER_OF_DAYS_BEFORE_CHRISTMAS);

	expect($message)->toBe('Dear Dasher, please return from North Pole in 17 day(s) to be ready and rest before Christmas.');
});

it('detects overdue reindeer', function (): void {
	$overdue = $this->communicator->isOverdue(DASHER, NORTH_POLE, NUMBER_OF_DAYS_BEFORE_CHRISTMAS, NUMBER_OF_DAYS_BEFORE_CHRISTMAS, $this->logger);

	expect($overdue)->toBeTrue()->
		and($this->logger->loggedMessage())->toBe('Overdue for Dasher located North Pole.');
});

it('returns false when no overdue', function (): void {
	$overdue = $this->communicator->isOverdue(DASHER, NORTH_POLE, 21, NUMBER_OF_DAYS_BEFORE_CHRISTMAS, $this->logger);

	expect($overdue)->toBeFalse();
});
