<?php

namespace Delivery;

class Building
{
    public static function whichFloor(string $instructions): int
    {
        $val = [];

        for ($i = 0; $i < strlen($instructions); $i++) {
            $c = $instructions[$i];

            if (strpos($instructions, "🧝")) {
                $j = ($c === ')') ? 3 : -2;
                $val[] = [$c, $j];
            } else if (strpos($instructions, "🧝") === false) {
                $val[] = [$c, ($c === '(') ? 1 : -1];
            } else {
                $val[] = [$c, ($c === '(') ? 42 : -2];
            }
        }

        $result = 0;
        foreach ($val as $kp) {
            $result += $kp[1];
        }

        return $result;
    }
}