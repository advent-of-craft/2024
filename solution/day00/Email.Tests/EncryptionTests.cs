using System.Security.Cryptography;
using System.Text;
using FluentAssertions;
using FsCheck;
using Xunit;
using Xunit.Abstractions;

namespace Email.Tests;

public class EncryptionTests(ITestOutputHelper testOutputHelper)
{
    private readonly Encryption _encryption = new(
        new Configuration(Key: ConvertKey("Advent Of Craft"), Iv: ConvertIv("2024"))
    );

    [Fact]
    public void Encrypt_A_String()
        => _encryption
            .Encrypt("Unlock Your Potential with the Advent Of Craft Calendar!")
            .Should()
            .Be("L7wht/YddOoTvYvrc+wFcZhtXNvZ2cHFxq9ND27h1Ovv/aWLxN8lWv1xMsguM/R4Yodk3rn9cppI+YarggtPjA==");

    [Fact]
    public void EncryptDecrypt_ShouldReturnOriginalString()
        // It is a Property-Based test that checks the below property
        // for all x (x: valid string) -> decrypt(encrypt(x)) == x
        // I'm pretty sure we will talk about this concept during our Journey 🎅
        => Prop.ForAll<string>(
            Arb.Default.String().Filter(str => !string.IsNullOrEmpty(str)),
            plainText =>
                _encryption.Decrypt(
                    _encryption.Encrypt(plainText)
                ) == plainText
        ).QuickCheckThrowOnFailure();

    [Fact]
    public void Decrypt_Email()
    {
        var decryptedContent = _encryption.Decrypt(FileUtils.LoadFile("EncryptedEmail.txt"));
        decryptedContent.Should().NotBeNullOrEmpty();
        testOutputHelper.WriteLine(decryptedContent);
    }

    private static string ConvertKey(string key)
        => Convert.ToBase64String(
            SHA256.HashData(Encoding.UTF8.GetBytes(key))
        );

    private static string ConvertIv(string iv)
        => Convert.ToBase64String(
            MD5.HashData(Encoding.UTF8.GetBytes(iv))
        );
}