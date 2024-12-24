<?php

namespace Tests;

use DateTime;
use DateTimeZone;

class Time
{
    public static DateTime $NOW;
    public static $PROVIDER;

    /**
     * @throws \Exception
     */
    public static function init(): void
    {
        self::$NOW = new DateTime('2024-12-24 23:30:45', new DateTimeZone('UTC'));
        self::$PROVIDER = function () {
            return self::$NOW;
        };
    }
}

Time::init();
