<?php

namespace TimeCapsule;

class Browser
{
    public static function open(string $filePath): void
    {
        if (PHP_OS_FAMILY === 'Windows') {
            shell_exec('start ' . escapeshellarg($filePath));
        } elseif (PHP_OS_FAMILY === 'Darwin') {
            shell_exec('open ' . escapeshellarg($filePath));
        } else {
            shell_exec('xdg-open ' . escapeshellarg($filePath));
        }
    }
}