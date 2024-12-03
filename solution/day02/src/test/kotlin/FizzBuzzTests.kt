import games.FizzBuzz
import io.kotest.assertions.arrow.core.shouldBeNone
import io.kotest.assertions.arrow.core.shouldBeSome
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData

class FizzBuzzTests : FunSpec({
    context("returns its numbers representation") {
        withData(
            ValidInput(1, "1"),
            ValidInput(67, "67"),
            ValidInput(82, "82"),
            ValidInput(3, "Fizz"),
            ValidInput(48, "Fizz"),
            ValidInput(66, "FizzBang"),
            ValidInput(99, "FizzBang"),
            ValidInput(5, "Buzz"),
            ValidInput(50, "Buzz"),
            ValidInput(85, "Buzz"),
            ValidInput(15, "FizzBuzz"),
            ValidInput(30, "FizzBuzz"),
            ValidInput(45, "FizzBuzz"),
            ValidInput(7, "Whizz"),
            ValidInput(28, "Whizz"),
            ValidInput(77, "WhizzBang"),
            ValidInput(21, "FizzWhizz"),
            ValidInput(42, "FizzWhizz"),
            ValidInput(84, "FizzWhizz"),
            ValidInput(35, "BuzzWhizz"),
            ValidInput(70, "BuzzWhizz"),
            ValidInput(11, "Bang"),
            ValidInput(44, "Bang"),
            ValidInput(55, "BuzzBang")
        ) { (input, expectedResult) ->
            FizzBuzz.convert(Configuration.mapping, input).shouldBeSome(expectedResult)
        }
    }

    context("fails for numbers out of range") {
        withData(0, -1, 101) { x ->
            FizzBuzz.convert(Configuration.mapping, x).shouldBeNone()
        }
    }
})

data class ValidInput(val input: Int, val expectedResult: String)