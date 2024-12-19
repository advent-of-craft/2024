import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.scalatest.prop.TableDrivenPropertyChecks.*
import travel.SantaTravelCalculator
import travel.SantaTravelCalculator.*

class TravelCalculatorSpec extends AnyFunSuite with Matchers {
  test("should calculate the distance correctly") {
    forAll(
      Table(
        ("numberOfReindeers", "expectedDistance"),
        (1, 1),
        (2, 3),
        (5, 31),
        (10, 1023),
        (20, 1048575),
        (30, 1073741823)
      )) { (numberOfReindeers: Int, expectedDistance: Int) =>
      calculateTotalDistanceRecursively(numberOfReindeers) shouldBe expectedDistance
    }
  }

  test("should fail for numbers greater than 32") {
    forAll(
      Table(
        "numberOfReindeers",
        32,
        50
      )
    ) { (numberOfReindeers: Int) =>
      val thrown = the[ArithmeticException] thrownBy {
        calculateTotalDistanceRecursively(numberOfReindeers)
      }
      thrown.getMessage shouldBe "Integer overflow"
    }
  }
}