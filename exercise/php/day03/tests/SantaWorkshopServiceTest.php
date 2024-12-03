<?php

declare(strict_types = 1);

use Preparation\Gift;
use Preparation\SantaWorkshopService;

const RECOMMENDED_AGE = 'recommendedAge';

beforeEach(function (): void {
	$this->service = new SantaWorkshopService;
});

it('prepares a valid gift', function (): void {
	$gift = $this->service->prepareGift('Bitzee', 3, 'Purple', 'Plastic');

	expect($gift)->toBeInstanceOf(Gift::class);
});

it('retrieves attribute on gift', function (): void {
	$gift = $this->service->prepareGift('Furby', 1, 'Multi', 'Cotton');
	$gift->addAttribute(RECOMMENDED_AGE, '3');

	expect($gift->recommendedAge())->toBe(3);
});

it('fails for a too heavy gift', function (): void {
	$prepareGift = fn () => $this->service->prepareGift('Dog-E', 6, 'White', 'Metal');

	expect($prepareGift)->toThrow(InvalidArgumentException::class, 'Gift is too heavy for Santa\'s sleigh');
});
