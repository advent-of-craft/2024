import eid.EID
import org.scalatest.EitherValues
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class EIDTests extends AnyFunSuite with Matchers with EitherValues {
  test("EID.parse should return valid EID for correct input") {
    val result = EID.parse("2374025")

    result.value.toString shouldBe "2374025";
  }
}
