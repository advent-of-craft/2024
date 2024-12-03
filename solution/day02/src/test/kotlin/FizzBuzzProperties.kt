import games.FizzBuzz
import games.MAX
import games.MIN
import io.kotest.core.spec.style.StringSpec
import io.kotest.property.Arb
import io.kotest.property.arbitrary.filter
import io.kotest.property.arbitrary.int
import io.kotest.property.forAll

fun Map<Int, String>.generateValidStrings(): List<String> =
    this.values + flatMap { first ->
        filter { second -> second.key > first.key }
            .map { second -> first.key * second.key to first.value + second.value }
    }.toMap().values

fun validStringsFor(x: Int): List<String> = Configuration.mapping.generateValidStrings() + x.toString()
val validNumbers = Arb.int(MIN..MAX)

class FizzBuzzProperties : StringSpec({

    "parse return a valid string for numbers between 1 and 100" {
        forAll(validNumbers) { x ->
            FizzBuzz.convert(Configuration.mapping, x).isSome { result -> validStringsFor(x).contains(result) }
        }
    }

    "parse fail for numbers out of range" {
        forAll(Arb.int().filter { i -> i < MIN || i > MAX }) { x ->
            FizzBuzz.convert(Configuration.mapping, x).isNone()
        }
    }

    "parse fail for valid numbers with no configuration" {
        forAll(validNumbers) { x ->
            FizzBuzz.convert(emptyMap(), x).isNone()
        }
    }
})