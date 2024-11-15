package email

import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

data class Configuration(val key: String, val iv: String)

private const val AES = "AES"

class Encryption(private val configuration: Configuration) {
    fun encrypt(plainText: String): String =
        Base64.getEncoder().encodeToString(
            createCipher(Cipher.ENCRYPT_MODE).doFinal(
                plainText.toByteArray(Charsets.UTF_8)
            )
        )

    fun decrypt(encryptedText: String): String =
        String(
            createCipher(Cipher.DECRYPT_MODE).doFinal(
                decode(encryptedText)
            ), Charsets.UTF_8
        )

    private fun createCipher(mode: Int): Cipher =
        Cipher.getInstance("${AES}/CBC/PKCS5Padding")
            .also { c ->
                c.init(
                    mode,
                    SecretKeySpec(decode(configuration.key), AES),
                    IvParameterSpec(decode(configuration.iv))
                )
            }

    private fun decode(x: String): ByteArray? = Base64.getDecoder().decode(x)
}