<?php

namespace Core\Control;

use Exception;

class SleighNotStartedException extends Exception
{
    public function __construct()
    {
        parent::__construct("The sleigh is not started. Please start the sleigh before any other action...");
    }
}
