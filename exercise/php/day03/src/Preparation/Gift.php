<?php

declare(strict_types = 1);

namespace Preparation;

class Gift
{
	private string $name;

	private float $weight;

	private string $color;

	private string $material;

	private array $attributes = [];

	public function __construct(string $name, float $weight, string $color, string $material)
	{
		$this->name = $name;
		$this->weight = $weight;
		$this->color = $color;
		$this->material = $material;
	}

	public function __toString(): string
	{
		return "A {$this->color}-colored {$this->name} weighting {$this->weight} kg made in {$this->material}";
	}

	public function addAttribute(string $key, string $value): void
	{
		$this->attributes[$key] = $value;
	}

	public function recommendedAge(): int
	{
		return isset($this->attributes['recommendedAge']) && is_numeric($this->attributes['recommendedAge']) ?
			(int) $this->attributes['recommendedAge'] :
			0;
	}
}
