<?php

use Children\ChildMapper;
use Children\Db2\X5T78;
use Children\DTOs\Gender;

it('map X5T78 to girl', function () {
    $db2Child = new X5T78();
    $db2Child->id = uniqid();
    $db2Child->n1 = 'Alice';
    $db2Child->n2 = 'Marie';
    $db2Child->n3 = 'Smith';
    $db2Child->cityOfBirthPc = 'Paradise';
    $db2Child->personBd = '2017-03-19';
    $db2Child->salutation = 'Girl';
    $db2Child->stNum = '123';
    $db2Child->stName = 'Sunny Street';
    $db2Child->stC = 'Paradise';
    $db2Child->stCid = '99';

    $child = ChildMapper::toDto($db2Child);

    expect($child->id)->toBe($db2Child->id)
        ->and($child->firstName)->toBe($db2Child->n1)
        ->and($child->middleName)->toBe($db2Child->n2)
        ->and($child->lastName)->toBe($db2Child->n3)
        ->and($child->birthCity)->toBe($db2Child->cityOfBirthPc)
        ->and($child->birthDate)->toBe($db2Child->personBd)
        ->and($child->gender)->toBe(Gender::Girl)
        ->and($child->address)->not->toBeNull()
        ->and($child->address->number)->toBe($db2Child->stNum)
        ->and($child->address->street)->toBe($db2Child->stName)
        ->and($child->address->city)->toBe($db2Child->stC)
        ->and($child->address->countryId)->toBe(99);
});


it('map X5T78 to child for a boy', function () {
    $db2Child = new X5T78();
    $db2Child->id = uniqid();
    $db2Child->n1 = 'Bob';
    $db2Child->n2 = '';
    $db2Child->n3 = 'Brown';
    $db2Child->cityOfBirthPc = 'Paradise';
    $db2Child->personBd = '2021-09-01';
    $db2Child->salutation = 'Boy';
    $db2Child->stNum = '9';
    $db2Child->stName = 'Oak Street';
    $db2Child->stC = 'Paradise';
    $db2Child->stCid = '98988';

    $child = ChildMapper::toDto($db2Child);

    expect($child->id)->toBe($db2Child->id)
        ->and($child->firstName)->toBe($db2Child->n1)
        ->and($child->middleName)->toBe($db2Child->n2)
        ->and($child->lastName)->toBe($db2Child->n3)
        ->and($child->birthCity)->toBe($db2Child->cityOfBirthPc)
        ->and($child->birthDate)->toBe($db2Child->personBd)
        ->and($child->gender)->toBe(Gender::Boy)
        ->and($child->address)->not->toBeNull()
        ->and($child->address->number)->toBe($db2Child->stNum)
        ->and($child->address->street)->toBe($db2Child->stName)
        ->and($child->address->city)->toBe($db2Child->stC)
        ->and($child->address->countryId)->toBe(98988);
});
