package communication

import doubles.TestLogger
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class SantaCommunicatorTest : DescribeSpec({
    val numberOfDaysToRest = 2
    val numberOfDayBeforeChristmas = 24
    val logger = TestLogger()
    lateinit var communicator: SantaCommunicator

    beforeEach {
        communicator = SantaCommunicator(numberOfDaysToRest)
    }

    describe("composeMessage") {
        it("should compose correct message") {
            communicator
                .composeMessage(
                    "Dasher",
                    "North Pole",
                    5,
                    numberOfDayBeforeChristmas
                ) shouldBe "Dear Dasher, please return from North Pole in 17 day(s) to be ready and rest before Christmas."
        }
    }

    describe("isOverdue") {
        it("should detect overdue reindeer") {
            val overdue = communicator.isOverdue(
                "Dasher",
                "North Pole",
                numberOfDayBeforeChristmas,
                numberOfDayBeforeChristmas,
                logger
            )

            overdue shouldBe true
            logger.getLog() shouldBe "Overdue for Dasher located North Pole."
        }

        it("should return false when not overdue") {
            communicator.isOverdue(
                "Dasher",
                "North Pole",
                numberOfDayBeforeChristmas - numberOfDaysToRest - 1,
                numberOfDayBeforeChristmas,
                logger
            ) shouldBe false
        }
    }
})
