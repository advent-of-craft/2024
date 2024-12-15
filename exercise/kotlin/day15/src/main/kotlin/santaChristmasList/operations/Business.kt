package santaChristmasList.operations

class Business(
    private val factory: Factory,
    private val inventory: Inventory,
    private val wishList: WishList
) {
    fun loadGiftsInSleigh(vararg children: Child): Sleigh {
        val sleigh = Sleigh()
        for (child in children) {
            val gift = wishList.identifyGift(child)
            if (gift != null) {
                val manufacturedGift = factory.findManufacturedGift(gift)
                if (manufacturedGift != null) {
                    val finalGift = inventory.pickUpGift(manufacturedGift.barCode)
                    if (finalGift != null) {
                        sleigh[child] = "Gift: ${finalGift.name} has been loaded!"
                    }
                }
            }
        }
        return sleigh
    }
}