using System.Security.Cryptography;

namespace Email
{
    public class Encryption(Configuration configuration)
    {
        public string Encrypt(string plainText)
        {
            using var aes = CreateAes(configuration);
            var encryptor = aes.CreateEncryptor(aes.Key, aes.IV);

            using var memoryStream = new MemoryStream();
            using (var cryptoStream = new CryptoStream(memoryStream, encryptor, CryptoStreamMode.Write))
            using (var sw = new StreamWriter(cryptoStream))
            {
                sw.Write(plainText);
            }

            return Convert.ToBase64String(memoryStream.ToArray());
        }

        public string Decrypt(string encryptedText)
        {
            using var aes = CreateAes(configuration);
            var decryptor = aes.CreateDecryptor(aes.Key, aes.IV);

            using var memoryStream = new MemoryStream(Convert.FromBase64String(encryptedText));
            using var cryptoStream = new CryptoStream(memoryStream, decryptor, CryptoStreamMode.Read);
            using var streamReader = new StreamReader(cryptoStream);

            return streamReader.ReadToEnd();
        }

        private static Aes CreateAes(Configuration configuration)
        {
            var aes = Aes.Create();
            var config = FromBase64String(configuration);
            aes.Key = config.key;
            aes.IV = config.iv;

            return aes;
        }

        private static (byte[] key, byte[] iv) FromBase64String(Configuration configuration)
            => (Convert.FromBase64String(configuration.Key), Convert.FromBase64String(configuration.Iv));
    }
}