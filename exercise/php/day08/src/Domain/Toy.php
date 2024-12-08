<?php

declare(strict_types = 1);

namespace Domain;

class Toy
{
	public const UNASSIGNED = 'UNASSIGNED';

	public const IN_PRODUCTION = 'IN_PRODUCTION';

	public const COMPLETED = 'COMPLETED';

	private string $name;

	private string $state;

	public function __construct(string $name, string $state)
	{
		$this->name = $name;
		$this->state = $state;
	}

	public function getName(): string
	{
		return $this->name;
	}

	public function getState(): string
	{
		return $this->state;
	}

	public function setState(string $state): void
	{
		$this->state = $state;
	}
}
