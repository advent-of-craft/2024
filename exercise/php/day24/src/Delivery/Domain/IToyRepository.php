<?php

namespace Delivery\Domain;

interface IToyRepository
{
    public function findById(string $id): ?Toy;

    public function save(Toy $toy): void;

    public function findByName(string $toyName): ?Toy;
}
