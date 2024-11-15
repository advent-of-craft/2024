package email

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.string
import io.kotest.property.checkAll
import java.nio.file.Paths
import java.security.MessageDigest
import java.util.*
import kotlin.io.path.readText

class EncryptionTest : StringSpec({
    val encryption = Encryption(
        Configuration(
            key = convertKey("Advent Of Craft"),
            iv = convertIv("2024")
        )
    )

    "should encrypt a string" {
        encryption
            .encrypt("Unlock Your Potential with the Advent Of Craft Calendar!")
            .shouldBe("L7wht/YddOoTvYvrc+wFcZhtXNvZ2cHFxq9ND27h1Ovv/aWLxN8lWv1xMsguM/R4Yodk3rn9cppI+YarggtPjA==")
    }

    //It is a Property-Based test that checks the below property
    //I'm pretty sure we will talk about this concept during our Journey 🎅
    "for all x (x: valid string) -> decrypt(encrypt(x)) == x" {
        checkAll(Arb.string(1..100)) { plainText ->
            encryption.decrypt(
                encryption.encrypt(plainText)
            ) shouldBe plainText
        }
    }
})

private fun convertKey(key: String): String {
    val sha256 = MessageDigest.getInstance("SHA-256")
    val keyBytes = sha256.digest(key.toByteArray(Charsets.UTF_8))
    return Base64.getEncoder().encodeToString(keyBytes)
}

private fun convertIv(iv: String): String {
    val md5 = MessageDigest.getInstance("MD5")
    val ivBytes = md5.digest(iv.toByteArray(Charsets.UTF_8))
    return Base64.getEncoder().encodeToString(ivBytes)
}

private fun loadFile(fileName: String): String =
    Paths.get(requireNotNull(EncryptionTest::class.java.classLoader.getResource(fileName)) {
        "File not found: $fileName"
    }.toURI()).readText(Charsets.UTF_8)