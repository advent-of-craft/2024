<?php

declare(strict_types = 1);

namespace Services;

use Domain\Toy;
use Domain\ToyRepository;

class ToyProductionService
{
	private ToyRepository $repository;

	public function __construct(ToyRepository $repository)
	{
		$this->repository = $repository;
	}

	public function assignToyToElf(string $toyName): void
	{
		$toy = $this->repository->findByName($toyName);

		if (null !== $toy && $toy->getState() === Toy::UNASSIGNED) {
			$toy->setState(Toy::IN_PRODUCTION);
			$this->repository->save($toy);
		}
	}
}
