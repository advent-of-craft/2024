import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers
import gifts._

class SantaSpec extends AnyFunSpec with Matchers {
  val Playstation: Toy = Toy("playstation")
  val Ball: Toy = Toy("ball")
  val Plush: Toy = Toy("plush")

  describe("Santa's gift selection process") {
    it("should give the third choice to a naughty child") {
      val bobby = new Child("bobby", "naughty")
      bobby.setWishlist(Playstation, Plush, Ball)
      val santa = new Santa()
      santa.addChild(bobby)

      santa.chooseToyForChild("bobby") shouldEqual Some(Ball)
    }

    it("should give the second choice to a nice child") {
      val bobby = new Child("bobby", "nice")
      bobby.setWishlist(Playstation, Plush, Ball)
      val santa = new Santa()
      santa.addChild(bobby)

      santa.chooseToyForChild("bobby") shouldEqual Some(Plush)
    }

    it("should give the first choice to a very nice child") {
      val bobby = new Child("bobby", "very nice")
      bobby.setWishlist(Playstation, Plush, Ball)
      val santa = new Santa()
      santa.addChild(bobby)

      santa.chooseToyForChild("bobby") shouldEqual Some(Playstation)
    }

    it("should throw an exception if the child does not exist") {
      val santa = new Santa()
      val bobby = new Child("bobby", "very nice")
      bobby.setWishlist(Playstation, Plush, Ball)
      santa.addChild(bobby)

      an[NoSuchElementException] should be thrownBy santa.chooseToyForChild("alice")
    }
  }
}
