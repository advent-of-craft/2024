<?php

declare(strict_types = 1);

namespace Routine {
	class Routine
	{
		private EmailService $emailService;

		private ScheduleService $scheduleService;

		private ReindeerFeeder $reindeerFeeder;

		public function __construct(EmailService $emailService, ScheduleService $scheduleService, ReindeerFeeder $reindeerFeeder)
		{
			$this->emailService = $emailService;
			$this->scheduleService = $scheduleService;
			$this->reindeerFeeder = $reindeerFeeder;
		}

		public function start(): void
		{
			$this->scheduleService->organizeMyDay($this->scheduleService->todaySchedule());
			$this->reindeerFeeder->feedReindeers();
			$this->emailService->readNewEmails();
			$this->scheduleService->continueDay();
		}
	}
}
