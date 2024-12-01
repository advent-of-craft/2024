<?php

declare(strict_types = 1);

namespace Tests\doubles {

	use Communication\ILogger;

	class TestLogger implements ILogger
	{
		private ?string $message = null;

		public function log(string $message): void
		{
			$this->message = $message;
		}

		public function loggedMessage(): ?string
		{
			return $this->message;
		}
	}
}
