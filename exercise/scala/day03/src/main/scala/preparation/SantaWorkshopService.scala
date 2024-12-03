package preparation

class SantaWorkshopService {
  private val preparedGifts: scala.collection.mutable.ListBuffer[Gift] = scala.collection.mutable.ListBuffer()

  def prepareGift(giftName: String, weight: Double, color: String, material: String): Gift = {
    if (weight > 5) {
      throw new IllegalArgumentException("Gift is too heavy for Santa's sleigh")
    }

    val gift = new Gift(giftName, weight, color, material)
    preparedGifts += gift

    gift
  }
}