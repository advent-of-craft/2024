<?php

declare(strict_types = 1);

namespace Workshop;

class ElfWorkshop
{
	private $taskList = [];

	public function getTaskList()
	{
		return $this->taskList;
	}

	public function addTask($task): void
	{
		if (!empty($task)) {
			$this->taskList[] = $task;
		}
	}

	public function completeTask()
	{
		if (!empty($this->taskList)) {
			return array_shift($this->taskList);
		}

	}
}
