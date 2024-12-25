<?php

require_once 'Capsule.php';
require_once 'Browser.php';

use TimeCapsule\Capsule;
use TimeCapsule\Browser;

$timeCapsule = new Capsule();

echo "🕰️ Welcome to the Time Capsule 🎅\n";

if ($timeCapsule->hasPastMessage) {
    echo "\n📜 Message from your past self:\n";
    echo "Written on: " . $timeCapsule->timestamp->format('F d, Y H:i:s') . "\n";
    echo "💌 Message: " . $timeCapsule->pastMessage . "\n";
} else {
    echo "\n📜 No message from your past self yet.\n";
}

echo "✍️  Enter a message for your future self: ";
$message = trim(fgets(STDIN));

$timeCapsule->saveMessage($message);

echo "\n🎉 Your message has been saved and added to the Time Capsule!\n";
echo "Opening the Time Capsule in your browser...\n";

Browser::open(Capsule::FILE_PATH);

echo "🌟 Thank you for participating in the Craft Advent Calendar! 🌟\n";
