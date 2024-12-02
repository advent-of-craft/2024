import games.FizzBuzz
import org.scalacheck.Gen
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

import scala.runtime.stdLibPatches.Predef.assert

class FizzBuzzProperties extends AnyFunSuite with ScalaCheckPropertyChecks {
  val MIN: Int = FizzBuzz.MIN
  val MAX: Int = FizzBuzz.MAX

  val fizzBuzzStrings: List[String] = List("Fizz", "Buzz", "FizzBuzz")

  def validStringsFor(x: Int): List[String] = fizzBuzzStrings :+ x.toString

  test("parse return a valid string for numbers between 1 and 100") {
    forAll(Gen.chooseNum(MIN, MAX)) { x =>
      assert(
        FizzBuzz.convert(x)
          .exists(result => validStringsFor(x).contains(result))
      )
    }
  }

  test("parse fail for numbers out of range") {
    forAll(Gen.chooseNum(Int.MinValue, Int.MaxValue).suchThat(i => i < FizzBuzz.MIN || i > FizzBuzz.MAX)) { x =>
      assert(FizzBuzz.convert(x).isEmpty)
    }
  }
}