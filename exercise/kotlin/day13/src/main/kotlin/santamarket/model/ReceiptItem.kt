package santamarket.model

import kotlin.math.abs

data class ReceiptItem(
    val product: Product,
    val quantity: Double,
    val price: Double,
    val totalPrice: Double
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ReceiptItem) return false

        val tolerance = 0.001
        return product == other.product &&
                abs(price - other.price) < tolerance &&
                abs(totalPrice - other.totalPrice) < tolerance &&
                abs(quantity - other.quantity) < tolerance
    }

    override fun hashCode(): Int = arrayOf(product, price, totalPrice, quantity).contentHashCode()
}