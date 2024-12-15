package santaChristmasList.operations

class Factory : HashMap<Gift, ManufacturedGift>() {
    fun findManufacturedGift(gift: Gift): ManufacturedGift? {
        return this[gift]
    }
}

class Inventory : HashMap<String, Gift>() {
    fun pickUpGift(barCode: String): Gift? {
        return this[barCode]
    }
}

class WishList : HashMap<Child, Gift>() {
    fun identifyGift(child: Child): Gift? {
        return this[child]
    }
}