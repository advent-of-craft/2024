<?php

declare(strict_types = 1);

namespace Preparation;

use InvalidArgumentException;

class SantaWorkshopService
{
	private array $preparedGifts = [];

	public function prepareGift(string $giftName, float $weight, string $color, string $material): Gift
	{
		if (5.0 < $weight) {
			throw new InvalidArgumentException('Gift is too heavy for Santa\'s sleigh');
		}

		$gift = new Gift($giftName, $weight, $color, $material);
		$this->preparedGifts[] = $gift;

		return $gift;
	}
}
