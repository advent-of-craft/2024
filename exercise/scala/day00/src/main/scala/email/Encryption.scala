package email

import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.{IvParameterSpec, SecretKeySpec}

class Encryption(key: String, iv: String):
  private val keyBytes = Base64.getDecoder.decode(key)
  private val ivBytes = Base64.getDecoder.decode(iv)

  private val UTF8 = "UTF-8"
  private val AES = "AES"

  def encrypt(plainText: String): String =
    Base64.getEncoder.encodeToString(
      createCipher(Cipher.ENCRYPT_MODE).doFinal(
        plainText.getBytes(UTF8)
      )
    )

  def decrypt(encryptedText: String): String =
    new String(
      createCipher(Cipher.DECRYPT_MODE).doFinal(
        Base64.getDecoder.decode(encryptedText)
      ), UTF8
    )

  private def createCipher(mode: Int) = {
    val cipher = Cipher.getInstance(s"$AES/CBC/PKCS5Padding")
    val secretKey = new SecretKeySpec(keyBytes, AES)
    val ivSpec = new IvParameterSpec(ivBytes)

    cipher.init(mode, secretKey, ivSpec)
    cipher
  }
