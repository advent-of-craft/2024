<?php

namespace Core\Control;

class Dashboard
{
    public function displayStatus(string $message): void
    {
        echo $message . PHP_EOL;
    }
}
