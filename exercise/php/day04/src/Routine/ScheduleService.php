<?php

declare(strict_types = 1);

namespace Routine;

interface ScheduleService
{
	public function todaySchedule(): Schedule;

	public function organizeMyDay(Schedule $schedule): void;

	public function continueDay(): void;
}
