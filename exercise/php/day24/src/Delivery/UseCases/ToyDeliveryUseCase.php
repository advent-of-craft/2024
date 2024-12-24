<?php

namespace Delivery\UseCases;

use Delivery\Domain\IToyRepository;

class ToyDeliveryUseCase
{
    private IToyRepository $repository;

    public function __construct(IToyRepository $repository)
    {
        $this->repository = $repository;
    }

    /**
     * @throws \Exception
     */
    public function handle(DeliverToy $deliverToy): void
    {
        $foundToy = $this->repository->findByName(
            $deliverToy->getDesiredToy()
        );

        if (!$foundToy) {
            throw new \Exception("Oops we have a problem... we have not built the toy: {$deliverToy->getDesiredToy()}");
        }

        $foundToy->reduceStock();
        $this->repository->save($foundToy);
    }
}