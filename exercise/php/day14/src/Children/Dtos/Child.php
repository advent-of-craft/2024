<?php

namespace Children\DTOs;

class Child {
    public string $id;
    public ?string $firstName;
    public ?string $middleName;
    public ?string $lastName;
    public ?string $birthCity;
    public string $birthDate;
    public Gender $gender;
    public ?Address $address;
}

