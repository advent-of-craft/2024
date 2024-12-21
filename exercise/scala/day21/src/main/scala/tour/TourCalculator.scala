package tour

import java.time.{Duration, LocalTime}
import scala.util.{Either, Left, Right}

case class Step(time: LocalTime, label: String, deliveryTime: Int)

class TourCalculator(private var steps: List[Step]) {
  private var calculated: Boolean = false
  private var deliveryTime: Double = 0.0

  def calculate(): Either[String, String] = {
    if (steps.isEmpty) {
      Left("No locations !!!")
    } else {
      val result = new StringBuilder

      steps.sortBy(_.time).foreach { s =>
        if (!calculated) {
          this.deliveryTime += s.deliveryTime
          result.append(fLine(s, deliveryTime))
          result.append(System.lineSeparator())
        }
      }

      val str: String = formatDurationToHHMMSS(
        Duration.ofSeconds(deliveryTime.toLong)
      )
      result.append(s"Delivery time | $str")
      result.append(System.lineSeparator())
      calculated = true

      Right(result.toString)
    }
  }

  private def formatDurationToHHMMSS(duration: Duration): String =
    f"${duration.toHours}%02d:${duration.toMinutesPart}%02d:${duration.toSecondsPart}%02d"

  private def fLine(step: Step, x: Double): String = {
    if (step != null) {
      s"${step.time} : ${step.label} | ${step.deliveryTime} sec"
    } else throw new IllegalStateException()
  }
}
