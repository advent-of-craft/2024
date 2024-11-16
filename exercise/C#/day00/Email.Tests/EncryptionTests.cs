using System.Security.Cryptography;
using System.Text;
using FluentAssertions;
using FsCheck;

namespace Email.Tests;

public class EncryptionTests
{
    private readonly Encryption _encryption = new(new Configuration(ConvertKey("Advent Of Craft"), ConvertIv("2024")));

    [Theory]
    [InlineData("EncryptedString.txt")]
    [InlineData("EncryptedEmail.txt")]
    public async Task Decrypt_An_Encrypted_String(string encryptedFilePath)
    {
        string encryptedText = FileUtils.LoadFile(encryptedFilePath);
        string decryptedText = _encryption.Decrypt(encryptedText);
        var settings = new VerifySettings();
        settings.UseFileName(encryptedFilePath);

        // Verify against snapshot
        await Verify(decryptedText, settings);
    }

    [Theory]   
    [InlineData("DecryptedString.txt")]
    [InlineData("DecryptedEmail.txt")]
    public async Task Encrypt_A_String(string decryptedFilePath)
    {
        string decryptedText = FileUtils.LoadFile(decryptedFilePath);
        string encryptText = _encryption.Encrypt(decryptedText);
        var settings = new VerifySettings();
        settings.UseFileName(decryptedFilePath);

        // Verify against snapshot
        await Verify(encryptText, settings);
    }

    [Fact]
    public void EncryptDecrypt_ShouldReturnOriginalString()
        // It is a Property-Based test that checks the below property
        // for all x (x: valid string) -> decrypt(encrypt(x)) == x
        // I'm pretty sure we will talk about this concept during our Journey 🎅
        => Prop.ForAll<string>(Arb.Default.String()
                                  .Filter(str => !string.IsNullOrEmpty(str)),
                               plainText => _encryption.Decrypt(_encryption.Encrypt(plainText)) == plainText)
               .QuickCheckThrowOnFailure();

    private static string ConvertIv(string iv) => Convert.ToBase64String(MD5.HashData(Encoding.UTF8.GetBytes(iv)));

    private static string ConvertKey(string key) => Convert.ToBase64String(SHA256.HashData(Encoding.UTF8.GetBytes(key)));
}