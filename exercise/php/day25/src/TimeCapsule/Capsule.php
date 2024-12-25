<?php

namespace TimeCapsule;

use DateTime;

class Capsule
{
    private const TEMPLATE = 'timecapsule_template.html';
    public const FILE_PATH = 'timecapsule.html';

    public ?string $pastMessage = null;
    public ?DateTime $timestamp = null;
    public bool $hasPastMessage;

    public function __construct()
    {
        $this->hasPastMessage = $this->loadPastMessage();
    }

    private function loadPastMessage(): bool
    {
        if (!file_exists(self::FILE_PATH)) {
            return false;
        }

        $htmlContent = file_get_contents(self::FILE_PATH);
        $startIndex = strpos($htmlContent, '<!--MESSAGE_START-->');
        $endIndex = strpos($htmlContent, '<!--MESSAGE_END-->');

        if ($startIndex === false || $endIndex === false) {
            return false;
        }

        $this->pastMessage = trim(substr($htmlContent, $startIndex + 18, $endIndex - ($startIndex + 18)));
        $this->timestamp = new DateTime();
        $this->timestamp->setTimestamp(filemtime(self::FILE_PATH));

        return true;
    }

    public function saveMessage(string $message): void
    {
        if (!file_exists(self::TEMPLATE)) {
            throw new \RuntimeException('HTML template file not found!');
        }

        $templateContent = file_get_contents(self::TEMPLATE);
        $filledContent = str_replace(
            ['{{message}}', '{{timestamp}}'],
            [$message, (new DateTime())->format('F d, Y H:i:s')],
            $templateContent
        );

        file_put_contents(self::FILE_PATH, $filledContent);

        $this->timestamp = new DateTime();
        $this->pastMessage = $message;
    }
}