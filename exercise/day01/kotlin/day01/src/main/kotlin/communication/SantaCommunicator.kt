package communication

class SantaCommunicator(private val numberOfDaysToRest: Int) {

    fun composeMessage(
        reindeerName: String,
        currentLocation: String,
        numbersOfDaysForComingBack: Int,
        numberOfDaysBeforeChristmas: Int
    ): String {
        val daysBeforeReturn = daysBeforeReturn(numbersOfDaysForComingBack, numberOfDaysBeforeChristmas)
        return "Dear $reindeerName, please return from $currentLocation in $daysBeforeReturn day(s) to be ready and rest before Christmas."
    }

    fun isOverdue(
        reindeerName: String,
        currentLocation: String,
        numbersOfDaysForComingBack: Int,
        numberOfDaysBeforeChristmas: Int,
        logger: Logger
    ): Boolean {
        if (daysBeforeReturn(numbersOfDaysForComingBack, numberOfDaysBeforeChristmas) <= 0) {
            logger.log("Overdue for $reindeerName located $currentLocation.")
            return true
        }
        return false
    }

    private fun daysBeforeReturn(numbersOfDaysForComingBack: Int, numberOfDaysBeforeChristmas: Int): Int {
        return numberOfDaysBeforeChristmas - numbersOfDaysForComingBack - numberOfDaysToRest
    }
}