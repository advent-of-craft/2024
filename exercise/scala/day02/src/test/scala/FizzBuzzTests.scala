import games.FizzBuzz
import org.scalatest.OptionValues
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.prop.TableDrivenPropertyChecks.*
import org.scalatest.prop.{TableFor1, TableFor2}

class FizzBuzzTests extends AnyFunSpec with Matchers with OptionValues {
  describe("FizzBuzz") {
    it("returns its numbers representation") {
      val validInputs: TableFor2[Int, String] = Table(
        ("input", "expectedResult"),
        (1, "1"),
        (67, "67"),
        (82, "82"),
        (3, "Fizz"),
        (66, "Fizz"),
        (99, "Fizz"),
        (5, "Buzz"),
        (50, "Buzz"),
        (85, "Buzz"),
        (15, "FizzBuzz"),
        (30, "FizzBuzz"),
        (45, "FizzBuzz")
      )

      forAll(validInputs) { (input: Int, expectedResult: String) =>
        FizzBuzz.convert(input).value shouldEqual expectedResult
      }
    }

    it("fails for numbers out of range") {
      val outOfRangeInputs: TableFor1[Int] = Table(
        "input",
        0,
        -1,
        101
      )

      forAll(outOfRangeInputs) { (x: Int) =>
        FizzBuzz.convert(x) should be(None)
      }
    }
  }
}