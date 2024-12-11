import christmas.Preparation
import christmas.Preparation.ToyType
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.scalatest.prop.TableDrivenPropertyChecks.*
import org.scalatest.prop.{TableFor2, TableFor4}

class PreparationSpec extends AnyFunSuite with Matchers {

  test("prepareGifts should return the correct preparation message") {
    val testData: TableFor2[Int, String] = Table(
      ("numberOfGifts", "expected"),
      (-1, "No gifts to prepare."),
      (0, "No gifts to prepare."),
      (1, "Elves will prepare the gifts."),
      (49, "Elves will prepare the gifts."),
      (50, "Santa will prepare the gifts.")
    )

    forAll(testData) { (numberOfGifts: Int, expected: String) =>
      Preparation.prepareGifts(numberOfGifts) should be(expected)
    }
  }

  test("categorizeGift should return the correct category based on age") {
    val testData: TableFor2[Int, String] = Table(
      ("age", "expectedCategory"),
      (1, "Baby"),
      (3, "Toddler"),
      (6, "Child"),
      (13, "Teen")
    )

    forAll(testData) { (age: Int, expectedCategory: String) =>
      Preparation.categorizeGift(age) should be(expectedCategory)
    }
  }

  test("ensureToyBalance should return the correct balance check") {
    val testData: TableFor4[ToyType, Int, Int, Boolean] = Table(
      ("toyType", "toysCount", "totalToys", "expected"),
      (ToyType.EDUCATIONAL, 25, 100, true),
      (ToyType.FUN, 30, 100, true),
      (ToyType.CREATIVE, 20, 100, true),
      (ToyType.EDUCATIONAL, 20, 100, false),
      (ToyType.FUN, 29, 100, false),
      (ToyType.CREATIVE, 15, 100, false)
    )

    forAll(testData) { (toyType: ToyType, toysCount: Int, totalToys: Int, expected: Boolean) =>
      Preparation.ensureToyBalance(toyType, toysCount, totalToys) should be(expected)
    }
  }
}