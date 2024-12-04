<?php

declare(strict_types = 1);

namespace Routine {
	interface EmailService
	{
		public function readNewEmails(): void;
	}
}
