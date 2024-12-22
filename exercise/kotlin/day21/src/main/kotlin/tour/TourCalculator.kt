package tour

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import java.time.Duration
import java.time.LocalTime

data class Step(val time: LocalTime, val label: String, val deliveryTime: Int)

class TourCalculator(private var steps: List<Step>) {
    private var calculated: Boolean = false
    private var deliveryTime: Double = 0.0

    fun calculate(): Either<String, String> {
        if (steps.isEmpty()) {
            return "No locations !!!".left()
        } else {
            val result = StringBuilder()

            steps.sortedBy { it.time }.forEach { s ->
                if (!calculated) {
                    this.deliveryTime += s.deliveryTime
                    result.appendLine(fLine(s, deliveryTime))
                }
            }

            val str: String = formatDurationToHHMMSS(
                Duration.ofSeconds(
                    deliveryTime.toLong()
                )
            )
            result.appendLine("Delivery time | $str")
            calculated = true

            return result.toString().right()
        }
    }

    private fun formatDurationToHHMMSS(duration: Duration): String =
        "%02d:%02d:%02d".format(
            duration.toHours(),
            duration.toMinutesPart(),
            duration.toSecondsPart()
        )

    private fun fLine(step: Step?, x: Double): String {
        if (step != null) {
            return step?.let {
                "${it.time} : ${it.label} | ${it.deliveryTime} sec"
            } ?: throw IllegalStateException()
        } else throw IllegalStateException()
    }
}
