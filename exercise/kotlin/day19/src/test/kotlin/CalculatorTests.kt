package travel.tests

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe
import travel.SantaTravelCalculator

class CalculatorTests : FunSpec({
    context("should calculate the distance correctly") {
        withData(
            1 to 1,
            2 to 3,
            5 to 31,
            10 to 1023,
            20 to 1048575,
            30 to 1073741823
        ) { (numberOfReindeers, expectedDistance) ->
            SantaTravelCalculator.calculateTotalDistanceRecursively(numberOfReindeers) shouldBe expectedDistance
        }
    }

    context("should fail for numbers greater than 32") {
        withData(32, 50) { numberOfReindeers ->
            shouldThrow<ArithmeticException> {
                SantaTravelCalculator.calculateTotalDistanceRecursively(numberOfReindeers)
            }.message shouldBe "Integer overflow"
        }
    }
})