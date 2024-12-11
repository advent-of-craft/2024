package christmas

import christmas.Preparation.ToyType.*

object Preparation {
  def prepareGifts(numberOfGifts: Int): String = {
    if (numberOfGifts <= 0) "No gifts to prepare."
    else if (numberOfGifts < 50) "Elves will prepare the gifts."
    else "Santa will prepare the gifts."
  }

  def categorizeGift(age: Int): String = {
    if (age <= 2) "Baby"
    else if (age <= 5) "Toddler"
    else if (age <= 12) "Child"
    else "Teen"
  }

  def ensureToyBalance(toyType: ToyType, toysCount: Int, totalToys: Int): Boolean = {
    val typePercentage = toysCount.toDouble / totalToys
    toyType match {
      case EDUCATIONAL => typePercentage >= 0.25
      case FUN => typePercentage >= 0.30
      case CREATIVE => typePercentage >= 0.20
    }
  }

  enum ToyType {
    case EDUCATIONAL, FUN, CREATIVE
  }
}