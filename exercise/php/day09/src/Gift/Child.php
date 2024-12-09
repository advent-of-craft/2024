<?php

declare(strict_types = 1);

namespace Gift;

class Child
{
	public function __construct(
		public string $name,
		public Behavior $behavior,
		public GiftRequest $giftRequest
	) {
	}
}
