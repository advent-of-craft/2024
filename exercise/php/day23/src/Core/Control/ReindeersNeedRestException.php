<?php

namespace Core\Control;

use Exception;

class ReindeersNeedRestException extends Exception
{
    public function __construct()
    {
        parent::__construct("The reindeer needs rest. Please park the sleigh...");
    }

}
