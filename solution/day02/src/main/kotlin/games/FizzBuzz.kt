package games

import arrow.core.None
import arrow.core.Option
import arrow.core.Some

const val MIN = 1
const val MAX = 100
typealias MatchingDivisors = Collection<String>
typealias Map = kotlin.collections.Map<Int, String>

object FizzBuzz {
    fun convert(mapping: Map, input: Int): Option<String> = when {
        isOutOfRange(input) -> None
        mapping.isEmpty() -> None
        else -> Some(convertSafely(mapping, input))
    }

    private fun isOutOfRange(input: Int) = input < MIN || input > MAX

    private fun convertSafely(mapping: Map, input: Int): String =
        mapping.matchingDivisors(input)
            .values
            .toResult(input)

    private fun Map.matchingDivisors(input: Int) = filter { (key, _) -> `is`(key, input) }
    private fun `is`(divisor: Int, input: Int): Boolean = input % divisor == 0

    private fun MatchingDivisors.toResult(input: Int) = when {
        any() -> joinToString("")
        else -> input.toString()
    }
}