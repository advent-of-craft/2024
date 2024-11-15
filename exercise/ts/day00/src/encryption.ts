import * as crypto from 'crypto';

export class Encryption {
    private readonly key: Buffer;
    private readonly iv: Buffer;

    constructor(key: string, iv: string) {
        this.key = Buffer.from(key, 'base64');
        this.iv = Buffer.from(iv, 'base64');
    }

    encrypt(plainText: string): string {
        const cipher = crypto.createCipheriv('aes-256-cbc', this.key, this.iv);
        let encrypted = cipher.update(plainText, 'utf-8', 'base64');
        encrypted += cipher.final('base64');

        return encrypted;
    }

    decrypt(encryptedText: string): string {
        const decipher = crypto.createDecipheriv('aes-256-cbc', this.key, this.iv);
        let decrypted = decipher.update(encryptedText, 'base64', 'utf-8');
        decrypted += decipher.final('utf-8');

        return decrypted;
    }
}