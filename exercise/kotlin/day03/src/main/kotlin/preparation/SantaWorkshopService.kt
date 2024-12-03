package preparation

class SantaWorkshopService {
    private val preparedGifts = mutableListOf<Gift>()

    fun prepareGift(giftName: String, weight: Double, color: String, material: String): Gift {
        if (weight > 5) {
            throw IllegalArgumentException("Gift is too heavy for Santa's sleigh")
        }

        val gift = Gift(giftName, weight, color, material)
        preparedGifts.add(gift)

        return gift
    }
}