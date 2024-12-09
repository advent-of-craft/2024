import gift.*
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class SantaServiceSpec extends AnyFunSuite with Matchers {
  val service = new SantaService()

  test("request is approved for nice child with feasible gift") {
    val niceChild = Child("Alice", "Thomas", Behavior.NICE, GiftRequest("Bicycle", true, Priority.NICE_TO_HAVE))
    service.evaluateRequest(niceChild) should be(true)
  }

  test("request is denied for naughty child") {
    val naughtyChild = Child("Noa", "Thierry", Behavior.NAUGHTY, GiftRequest("SomeToy", true, Priority.DREAM))
    service.evaluateRequest(naughtyChild) should be(false)
  }

  test("request is denied for nice child with infeasible gift") {
    val niceChildWithInfeasibleGift = Child("Charlie", "Joie", Behavior.NICE, GiftRequest("AnotherToy", false, Priority.DREAM))
    service.evaluateRequest(niceChildWithInfeasibleGift) should be(false)
  }
}