<?php

declare(strict_types = 1);

namespace Domain;

interface ToyRepository
{
	public function findByName(string $name): ?Toy;

	public function save(Toy $toy): void;
}
