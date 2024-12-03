import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers
import preparation.{Gift, SantaWorkshopService}

class SantaWorkshopServiceSpec extends AnyFunSpec with Matchers {
  describe("SantaWorkshopService") {
    val service = new SantaWorkshopService

    describe("prepareGift") {
      it("should prepare a gift with valid parameters") {
        val giftName = "Bitzee"
        val weight = 3.0
        val color = "Purple"
        val material = "Plastic"

        service.prepareGift(giftName, weight, color, material) should not be null
      }

      it("should retrieve an attribute to a gift") {
        val giftName = "Furby"
        val weight = 1.0
        val color = "Multi"
        val material = "Cotton"

        val gift = new Gift(giftName, weight, color, material)
        gift.addAttribute("recommendedAge", "3")

        gift.getRecommendedAge shouldBe 3
      }

      it("should fail if gift is too heavy") {
        val giftName = "Dog-E"
        val weight = 6.0
        val color = "White"
        val material = "Metal"

        val exception = intercept[IllegalArgumentException] {
          service.prepareGift(giftName, weight, color, material)
        }
        exception.getMessage shouldBe "Gift is too heavy for Santa's sleigh"
      }
    }
  }
}