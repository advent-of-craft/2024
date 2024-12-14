<?php

namespace Children;

use Children\Db2\X5T78;
use Children\DTOs\Child;
use Children\DTOs\Address;
use Children\DTOs\Gender;

class ChildMapper {
    public static function toDto(X5T78 $child): Child {
        $dto = new Child();
        $dto->id = $child->id;
        $dto->firstName = $child->n1;
        $dto->middleName = $child->n2;
        $dto->lastName = $child->n3;
        $dto->birthCity = $child->cityOfBirthPc;
        $dto->birthDate = $child->personBd;
        $dto->gender = Gender::from($child->salutation);
        $dto->address = new Address();
        $dto->address->number = $child->stNum;
        $dto->address->street = $child->stName;
        $dto->address->city = $child->stC;
        $dto->address->countryId = (int)$child->stCid;
        return $dto;
    }
}