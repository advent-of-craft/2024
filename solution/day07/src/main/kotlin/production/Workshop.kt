package production

import production.Status.Produced

class Workshop {
    private val gifts = mutableListOf<Gift>()

    fun addGift(gift: Gift) {
        gifts.add(gift)
    }

    fun completeGift(name: String): Gift? =
        gifts.find { it.name == name }
            ?.run {
                return this.copy(status = Produced)
            }
}