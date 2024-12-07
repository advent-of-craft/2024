<?php

use PHPUnit\Framework\Assert;
use Workshop\Gift;
use Workshop\Status;
use Workshop\Workshop;

it('completes a gift and sets its status to produced', function () {
    $workshop = new Workshop();
    $workshop->addGift(new Gift("1 Super Nintendo"));

    $completedGift = $workshop->completeGift("1 Super Nintendo");

    Assert::assertNotNull($completedGift);
    Assert::assertEquals(Status::PRODUCED, $completedGift->getStatus());
});

it('returns null when trying to complete a non-existing gift', function () {
    $workshop = new Workshop();
    $completedGift = $workshop->completeGift("NonExistingToy");

    Assert::assertNull($completedGift);
});