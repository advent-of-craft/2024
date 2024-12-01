<?php

declare(strict_types = 1);

namespace Communication {
	class SantaCommunicator
	{
		private int $numberOfDaysToRest;

		public function __construct(int $numberOfDaysToRest)
		{
			$this->numberOfDaysToRest = $numberOfDaysToRest;
		}

		public function composeMessage(string $reindeerName, string $currentLocation, int $numbersOfDaysForComingBack, int $numberOfDaysBeforeChristmas): string
		{
			$daysBeforeReturn = $this->daysBeforeReturn($numbersOfDaysForComingBack, $numberOfDaysBeforeChristmas);

			return "Dear {$reindeerName}, please return from {$currentLocation} in {$daysBeforeReturn} day(s) to be ready and rest before Christmas.";
		}

		public function isOverdue(string $reindeerName, string $currentLocation, int $numbersOfDaysForComingBack, int $numberOfDaysBeforeChristmas, ILogger $logger): bool
		{
			if ($this->daysBeforeReturn($numbersOfDaysForComingBack, $numberOfDaysBeforeChristmas) <= 0) {
				$logger->log("Overdue for {$reindeerName} located {$currentLocation}.");

				return true;
			}

			return false;
		}

		private function daysBeforeReturn(int $numbersOfDaysForComingBack, int $numberOfDaysBeforeChristmas): int
		{
			return $numberOfDaysBeforeChristmas - $numbersOfDaysForComingBack - $this->numberOfDaysToRest;
		}
	}
}
