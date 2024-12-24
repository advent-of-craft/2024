import communication.SantaCommunicator
import doubles.TestLogger
import org.scalatest.BeforeAndAfterEach
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class SantaCommunicatorSpec extends AnyFunSuite with Matchers with BeforeAndAfterEach {
  val numberOfDaysToRest = 2
  val numberOfDayBeforeChristmas = 24
  val logger = new TestLogger
  var communicator: SantaCommunicator = _

  override def beforeEach(): Unit = {
    communicator = new SantaCommunicator(numberOfDaysToRest)
  }

  test("composeMessage should compose correct message") {
    communicator.composeMessage(
      "Dasher",
      "North Pole",
      5,
      numberOfDayBeforeChristmas
    ) should be("Dear Dasher, please return from North Pole in 17 day(s) to be ready and rest before Christmas.")
  }

  test("isOverdue should detect overdue reindeer") {
    communicator.isOverdue(
      "Dasher",
      "North Pole",
      numberOfDayBeforeChristmas,
      numberOfDayBeforeChristmas,
      logger
    ) should be(true)
    logger.getLog should be(Some("Overdue for Dasher located North Pole."))
  }

  test("isOverdue should return false when not overdue") {
    communicator.isOverdue(
      "Dasher",
      "North Pole",
      numberOfDayBeforeChristmas - numberOfDaysToRest - 1,
      numberOfDayBeforeChristmas,
      logger
    ) should be(false)
  }
}