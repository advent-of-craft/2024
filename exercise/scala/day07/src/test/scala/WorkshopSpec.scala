import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import production.*

class WorkshopSpec extends AnyFunSuite with Matchers {
  private val toyName = "1 Super Nintendo"

  test("completing a gift should set its status to produced") {
    val workshop = new Workshop()
    workshop.addGift(Gift(toyName))

    val completedGift = workshop.completeGift(toyName)

    completedGift should not be None
    completedGift.get.status should be(Status.Produced)
  }

  test("completing a non existing gift should return nothing") {
    val workshop = new Workshop()
    val completedGift = workshop.completeGift("UnExisting toy")

    completedGift should be(None)
  }
}