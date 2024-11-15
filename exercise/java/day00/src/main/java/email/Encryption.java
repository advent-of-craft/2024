package email;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Encryption {
    private final Configuration configuration;

    public Encryption(Configuration configuration) {
        this.configuration = configuration;
    }

    public String encrypt(String plainText) throws Exception {
        var cipher = createCipher(Cipher.ENCRYPT_MODE);
        var encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public String decrypt(String encryptedText) throws Exception {
        var cipher = createCipher(Cipher.DECRYPT_MODE);
        var decodedBytes = Base64.getDecoder().decode(encryptedText);
        var decryptedBytes = cipher.doFinal(decodedBytes);

        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    private Cipher createCipher(int mode) throws Exception {
        var cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        var secretKeySpec = new SecretKeySpec(Base64.getDecoder().decode(configuration.key()), "AES");
        var ivSpec = new IvParameterSpec(Base64.getDecoder().decode(configuration.iv()));
        cipher.init(mode, secretKeySpec, ivSpec);

        return cipher;
    }
}