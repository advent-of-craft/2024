package travel

object SantaTravelCalculator {
    fun calculateTotalDistanceRecursively(numberOfReindeers: Int): Int {
        if (numberOfReindeers == 1) return 1
        (2 * calculateTotalDistanceRecursively(numberOfReindeers - 1) + 1)
            .let { result ->
                if (result == -1) throw ArithmeticException("Integer overflow")
                return result
            }
    }
}