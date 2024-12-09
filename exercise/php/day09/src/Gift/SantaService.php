<?php

declare(strict_types = 1);

namespace Gift;

class SantaService
{
	public function evaluateRequest(Child $child): bool
	{
		return Behavior::NICE === $child->behavior && $child->giftRequest->isFeasible;
	}
}
