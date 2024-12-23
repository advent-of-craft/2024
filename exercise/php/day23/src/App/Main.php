<?php

namespace App;

use Core\Control\ControlSystem;
use Core\Control\SleighNotStartedException;
use Core\Control\ReindeersNeedRestException;

class Main
{
    public static function main()
    {
        $controlSystem = new ControlSystem();
        $controlSystem->startSystem();

        $handle = fopen("php://stdin", "r");
        $keepRunning = true;

        while ($keepRunning) {
            echo "Enter a command (ascend (a), descend (d), park (p), or quit (q)): ";
            $command = trim(fgets($handle));

            switch ($command) {
                case 'ascend':
                case 'a':
                    try {
                        $controlSystem->ascend();
                    } catch (ReindeersNeedRestException | SleighNotStartedException $e) {
                        echo $e->getMessage() . PHP_EOL;
                    }
                    break;
                case 'descend':
                case 'd':
                    try {
                        $controlSystem->descend();
                    } catch (SleighNotStartedException $e) {
                        echo $e->getMessage() . PHP_EOL;
                    }
                    break;
                case 'park':
                case 'p':
                    try {
                        $controlSystem->park();
                    } catch (SleighNotStartedException $e) {
                        echo $e->getMessage() . PHP_EOL;
                    }
                    break;
                case 'quit':
                case 'q':
                    $keepRunning = false;
                    break;
                default:
                    echo "Invalid command. Please try again." . PHP_EOL;
            }
        }

        fclose($handle);
        $controlSystem->stopSystem();
    }
}

Main::main();
