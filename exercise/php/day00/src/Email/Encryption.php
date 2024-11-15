<?php

namespace Email;

use RuntimeException;

class Encryption
{
    private string $key;
    private string $iv;

    public function __construct(string $key, string $iv)
    {
        // Decode Base64-encoded key and IV
        $this->key = base64_decode($key);
        $this->iv = base64_decode($iv);
    }

    public function encrypt(string $plainText): string
    {
        // Encrypt using AES-256-CBC with PKCS7 padding
        $ciphertext = openssl_encrypt(
            $plainText,
            'AES-256-CBC',
            $this->key,
            OPENSSL_RAW_DATA, // Raw binary data
            $this->iv
        );

        if ($ciphertext === false) {
            throw new RuntimeException('Encryption failed.');
        }

        // Return Base64-encoded result to match C# behavior
        return base64_encode($ciphertext);
    }

    public function decrypt(string $encryptedText): string
    {
        // Decode the Base64-encoded ciphertext
        $ciphertext = base64_decode($encryptedText);

        // Decrypt using AES-256-CBC with PKCS7 padding
        $decrypted = openssl_decrypt(
            $ciphertext,
            'AES-256-CBC',
            $this->key,
            OPENSSL_RAW_DATA, // Raw binary data
            $this->iv
        );

        if ($decrypted === false) {
            throw new RuntimeException('Decryption failed.');
        }

        return $decrypted;
    }
}
