package travel

object SantaTravelCalculator {
  def calculateTotalDistanceRecursively(numberOfReindeers: Int): Int = {
    if (numberOfReindeers == 1) 1
    else {
      val result = 2 * calculateTotalDistanceRecursively(numberOfReindeers - 1) + 1
      if (result < 0) throw new ArithmeticException("Integer overflow")
      result
    }
  }
}