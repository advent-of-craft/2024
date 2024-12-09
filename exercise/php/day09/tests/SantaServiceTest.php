<?php

declare(strict_types = 1);

use Gift\Behavior;
use Gift\Child;
use Gift\GiftRequest;
use Gift\Priority;
use Gift\SantaService;

beforeEach(function (): void {
	$this->service = new SantaService;
});

it('approves request for nice child with feasible gift', function (): void {
	$niceChild = new Child('Alice Thomas', Behavior::NICE, new GiftRequest('Bicycle', true, Priority::NICE_TO_HAVE));
	expect($this->service->evaluateRequest($niceChild))->toBeTrue();
});

it('denies request for naughty child', function (): void {
	$naughtyChild = new Child('Noa Thierry', Behavior::NAUGHTY, new GiftRequest('SomeToy', true, Priority::DREAM));
	expect($this->service->evaluateRequest($naughtyChild))->toBeFalse();
});

it('denies request for nice child with infeasible gift', function (): void {
	$niceChildWithInfeasibleGift = new Child('Charlie Joie', Behavior::NICE, new GiftRequest('AnotherToy', false, Priority::DREAM));
	expect($this->service->evaluateRequest($niceChildWithInfeasibleGift))->toBeFalse();
});
