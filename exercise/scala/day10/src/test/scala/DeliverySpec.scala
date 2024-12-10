import delivery.Building
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.scalatest.prop.TableDrivenPropertyChecks.*
import org.scalatest.prop.TableFor2

import scala.io.Source

class DeliverySpec extends AnyFunSuite with Matchers {

  import DeliverySpec.*

  val testData: TableFor2[String, Int] = Table(
    ("fileName", "expectedFloor"),
    ("1.txt", 0),
    ("2.txt", 3),
    ("3.txt", -1),
    ("4.txt", 53),
    ("5.txt", -3),
    ("6.txt", 2920)
  )

  test("return floor number based on instructions") {
    forAll(testData) { (fileName: String, expectedFloor: Int) =>
      val instructions = loadInstructionsFromFile(fileName)
      Building.whichFloor(instructions) should be(expectedFloor)
    }
  }
}

object DeliverySpec {
  def loadInstructionsFromFile(input: String): String = {
    val source = Source.fromResource(input)
    try source.mkString finally source.close()
  }
}
