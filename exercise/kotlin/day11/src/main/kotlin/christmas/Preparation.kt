package christmas

import christmas.ToyType.*

enum class ToyType {
    EDUCATIONAL, FUN, CREATIVE
}

object Preparation {
    fun prepareGifts(numberOfGifts: Int): String = when {
        numberOfGifts <= 0 -> "No gifts to prepare."
        numberOfGifts < 50 -> "Elves will prepare the gifts."
        else -> "Santa will prepare the gifts."
    }

    fun categorizeGift(age: Int): String = when {
        age <= 2 -> "Baby"
        age <= 5 -> "Toddler"
        age <= 12 -> "Child"
        else -> "Teen"
    }

    fun ensureToyBalance(toyType: ToyType, toysCount: Int, totalToys: Int): Boolean =
        (toysCount.toDouble() / totalToys)
            .let { typePercentage ->
                return when (toyType) {
                    EDUCATIONAL -> typePercentage >= 0.25
                    FUN -> typePercentage >= 0.30
                    CREATIVE -> typePercentage >= 0.20
                }
            }
}