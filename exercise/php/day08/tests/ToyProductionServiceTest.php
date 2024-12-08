<?php

declare(strict_types = 1);

use Domain\Toy;
use PHPUnit\Framework\Assert;
use Services\ToyProductionService;
use Tests\Doubles\InMemoryToyRepository;

it('assigns toy to elf and sets state to IN_PRODUCTION', function (): void {
	$repository = new InMemoryToyRepository;
	$service = new ToyProductionService($repository);
	$toyName = 'Train';

	$repository->save(new Toy($toyName, Toy::UNASSIGNED));

	$service->assignToyToElf($toyName);

	$toy = $repository->findByName($toyName);
	Assert::assertEquals(Toy::IN_PRODUCTION, $toy->getState());
});
