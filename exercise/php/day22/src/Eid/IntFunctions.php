<?php

namespace Eid;
class IntFunctions
{
    public static function toInt(string $str): ?int
    {
        if (preg_match("/^0+$/", $str)) {
            return 0;
        }

        $trimmedStr = ltrim($str, '0');
        if ($trimmedStr === '') {
            return 0;
        }
        $intVal = filter_var($trimmedStr, FILTER_VALIDATE_INT);

        return $intVal === false ? null : $intVal;
    }
}