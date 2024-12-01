package communication

class SantaCommunicator(private val numberOfDaysToRest: Int) {

  def composeMessage(
                      reindeerName: String,
                      currentLocation: String,
                      numbersOfDaysForComingBack: Int,
                      numberOfDaysBeforeChristmas: Int
                    ): String = {
    val daysBeforeReturn = this.daysBeforeReturn(numbersOfDaysForComingBack, numberOfDaysBeforeChristmas)
    s"Dear $reindeerName, please return from $currentLocation in $daysBeforeReturn day(s) to be ready and rest before Christmas."
  }

  def isOverdue(
                 reindeerName: String,
                 currentLocation: String,
                 numbersOfDaysForComingBack: Int,
                 numberOfDaysBeforeChristmas: Int,
                 logger: Logger): Boolean = {
    if (this.daysBeforeReturn(numbersOfDaysForComingBack, numberOfDaysBeforeChristmas) <= 0) {
      logger.log(s"Overdue for $reindeerName located $currentLocation.")
      true
    } else false
  }

  private def daysBeforeReturn(numbersOfDaysForComingBack: Int, numberOfDaysBeforeChristmas: Int): Int =
    numberOfDaysBeforeChristmas - numbersOfDaysForComingBack - numberOfDaysToRest
}