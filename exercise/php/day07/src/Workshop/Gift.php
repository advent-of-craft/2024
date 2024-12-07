<?php

namespace Workshop;

class Gift
{
    private string $name;
    private string $status;

    public function __construct(string $name, string $status = Status::PRODUCING)
    {
        $this->name = $name;
        $this->status = $status;
    }

    public function getName(): string
    {
        return $this->name;
    }

    public function getStatus(): string
    {
        return $this->status;
    }

    public function withStatus(string $status): Gift
    {
        return new Gift($this->name, $status);
    }
}