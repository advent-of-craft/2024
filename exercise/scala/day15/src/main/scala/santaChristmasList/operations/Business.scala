package santaChristmasList.operations

class Business(factory: Factory, inventory: Inventory, wishList: WishList) {
  def loadGiftsInSleigh(children: Child*): Sleigh = {
    val sleigh = new Sleigh()
    for (child <- children) {
      val gift = wishList.identifyGift(child)
      if (gift.isDefined) {
        val manufacturedGift = factory.findManufacturedGift(gift.get)
        if (manufacturedGift.isDefined) {
          val finalGift = inventory.pickUpGift(manufacturedGift.get.barCode)
          if (finalGift.isDefined) {
            sleigh.put(child, s"Gift: ${finalGift.get.name} has been loaded!")
          }
        }
      }
    }
    sleigh
  }
}