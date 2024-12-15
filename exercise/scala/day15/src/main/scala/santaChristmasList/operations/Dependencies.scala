package santaChristmasList.operations

class Factory extends collection.mutable.HashMap[Gift, ManufacturedGift] {
  def findManufacturedGift(gift: Gift): Option[ManufacturedGift] = {
    this.get(gift)
  }
}

class Inventory extends collection.mutable.HashMap[String, Gift] {
  def pickUpGift(barCode: String): Option[Gift] = {
    this.get(barCode)
  }
}

class WishList extends collection.mutable.HashMap[Child, Gift] {
  def identifyGift(child: Child): Option[Gift] = {
    this.get(child)
  }
}