using System.Security.Cryptography;
using System.Text;
using FluentAssertions;
using FsCheck;
using Xunit;

namespace Email.Tests;

public class EncryptionTests
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
    public void Decrypt_EncryptedEmailFile()
        => _encryption
            .Decrypt(FileUtils.LoadFile("EncryptedEmail.txt"))
            .Should()
            .Be("""
                Dear consultant,
                
                We are facing an unprecedented challenge in Christmas Town.
                
                The systems that keep our magical operations running smoothly are outdated, fragile, and in dire need of modernization. 
                We urgently require your expertise to ensure Christmas happens this year.
                Our town is located within a mountain circlet at the North Pole, surrounded by high peaks and protected by an advanced communication and shield system to hide it from the outside world.
                
                You have been selected for your exceptional skills and dedication. 
                Please report to the North Pole immediately. 
                
                Enclosed are your travel details and a non-disclosure agreement that you must sign upon arrival.
                Our dwarf friends from the security will receive and escort you in as soon as you check security.
                In the following days, you will receive bracelets to be able to pass through the magic shield.
                
                Time is of the essence.
                You must arrive before the beginning of December to be able to acclimate yourself with all the systems.
                
                We are counting on you to help save Christmas.
                
                Sincerely,
                
                Santa Claus 🎅
                """.Replace(Environment.NewLine, "\n"));

    private static string ConvertKey(string key)
        => Convert.ToBase64String(
            SHA256.HashData(Encoding.UTF8.GetBytes(key))
        );

    private static string ConvertIv(string iv)
        => Convert.ToBase64String(
            MD5.HashData(Encoding.UTF8.GetBytes(iv))
        );
}