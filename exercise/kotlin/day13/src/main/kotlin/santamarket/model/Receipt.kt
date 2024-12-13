package santamarket.model

class Receipt {
    private val items = mutableListOf<ReceiptItem>()
    private val discounts = mutableListOf<Discount>()

    fun getTotalPrice(): Double {
        var total = 0.0
        items.forEach { item -> total += item.totalPrice }
        discounts.forEach { discount -> total += discount.discountAmount }
        return total
    }

    fun addProduct(product: Product, quantity: Double, price: Double, totalPrice: Double) {
        items.add(ReceiptItem(product, quantity, price, totalPrice))
    }

    fun getItems(): List<ReceiptItem> = items.toList()

    fun addDiscount(discount: Discount) {
        discounts.add(discount)
    }

    fun getDiscounts(): List<Discount> = discounts.toList()

    override fun equals(other: Any?): Boolean =
        other is Receipt && items == other.items && discounts == other.discounts

    override fun hashCode(): Int = 31 * items.hashCode() + discounts.hashCode()
}
