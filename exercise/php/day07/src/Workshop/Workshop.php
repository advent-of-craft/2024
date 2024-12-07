<?php

namespace Workshop;
class Workshop
{
    private $gifts = [];

    public function addGift(Gift $gift): void
    {
        $this->gifts[] = $gift;
    }

    public function completeGift(string $name): ?Gift
    {
        foreach ($this->gifts as $gift) {
            if ($gift->getName() === $name) {
                return $gift->withStatus(Status::PRODUCED);
            }
        }

        return null;
    }
}
