import email.Encryption
import org.scalacheck.Gen
import org.scalacheck.Gen.*
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.must.Matchers
import org.scalatest.matchers.must.Matchers.{must, mustEqual, not}
import org.scalatest.prop.TableDrivenPropertyChecks.whenever
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

import java.nio.file.Paths
import java.security.MessageDigest
import java.util.Base64
import scala.io.Source

class EncryptionSpec extends AnyFunSuite with ScalaCheckPropertyChecks {
  private val key: String = convertKey("Advent Of Craft")
  private val iv = convertIv("2024")
  private val encryption = Encryption(key, iv)

  test("encrypt a string") {
    encryption.encrypt("Unlock Your Potential with the Advent Of Craft Calendar!") mustEqual "L7wht/YddOoTvYvrc+wFcZhtXNvZ2cHFxq9ND27h1Ovv/aWLxN8lWv1xMsguM/R4Yodk3rn9cppI+YarggtPjA=="
  }

  // It is a Property-Based test that checks the below property
  // I'm pretty sure we will talk about this concept during our Journey 🎅
  test("for all x (x: valid string) -> decrypt(encrypt(x)) == x") {
    forAll(alphaStr) { (x: String) =>
      whenever(x.nonEmpty) {
        encryption.decrypt(encryption.encrypt(x)) mustEqual x
      }
    }
  }

  def convertKey(key: String): String =
    val sha256 = MessageDigest.getInstance("SHA-256")
    val keyBytes = sha256.digest(key.getBytes("UTF-8"))
    Base64.getEncoder.encodeToString(keyBytes)

  def convertIv(iv: String): String =
    val md5 = MessageDigest.getInstance("MD5")
    val ivBytes = md5.digest(iv.getBytes("UTF-8"))
    Base64.getEncoder.encodeToString(ivBytes)

  private def loadFile(filename: String): String =
    val resourcePath = Paths.get(getClass.getResource(s"/$filename").toURI)
    val source = Source.fromFile(resourcePath.toFile)
    try source.getLines().mkString("\n")
    finally source.close()
}