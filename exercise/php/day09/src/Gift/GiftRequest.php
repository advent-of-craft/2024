<?php

declare(strict_types = 1);

namespace Gift;

class GiftRequest
{
	public function __construct(
		public string $giftName,
		public bool $isFeasible,
		public Priority $priority
	) {
	}
}
