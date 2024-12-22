<?php

namespace Eid;

class ParsingError
{
    private string $message;

    public function __construct(string $message)
    {
        $this->message = $message;
    }
}
