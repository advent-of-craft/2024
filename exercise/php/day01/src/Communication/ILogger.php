<?php

namespace Communication {
    interface ILogger
    {
        public function log(string $message): void;
    }
}

