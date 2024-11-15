<?php

use Email\Encryption;
use Faker\Factory as FakerFactory;
use function PHPUnit\Framework\assertEquals;
use function PHPUnit\Framework\assertNotEmpty;

uses()->beforeEach(function () {
    $this->faker = FakerFactory::create();
});

beforeEach(function () {
    $this->encryption = new Encryption(
        convertKey('Advent Of Craft'),
        convertIv('2024')
    );
});

test('encrypt a string', function () {
    $encryptedText = $this->encryption->encrypt('Unlock Your Potential with the Advent Of Craft Calendar!');
    assertEquals(
        'L7wht/YddOoTvYvrc+wFcZhtXNvZ2cHFxq9ND27h1Ovv/aWLxN8lWv1xMsguM/R4Yodk3rn9cppI+YarggtPjA==',
        $encryptedText
    );
});

test('encrypt and decrypt should return the original string', function () {
    $randomString = $this->faker->text(50);

    $encrypted = $this->encryption->encrypt($randomString);
    $decrypted = $this->encryption->decrypt($encrypted);

    assertEquals($randomString, $decrypted);
});

function convertKey(string $key): string
{
    return base64_encode(hash('sha256', $key, true));
}

function convertIv(string $iv): string
{
    return base64_encode(hash('md5', $iv, true));
}

function loadFile(string $fileName): string
{
    $filePath = __DIR__ . '/resources/' . $fileName;

    if (!file_exists($filePath)) {
        throw new InvalidArgumentException("File not found: $fileName");
    }

    return file_get_contents($filePath);
}