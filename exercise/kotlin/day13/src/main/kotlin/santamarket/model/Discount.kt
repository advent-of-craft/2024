package santamarket.model

import kotlin.math.abs

data class Discount(
    val product: Product,
    val description: String,
    val discountAmount: Double
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Discount) return false

        val tolerance = 0.001
        return product == other.product &&
                description == other.description &&
                abs(discountAmount - other.discountAmount) < tolerance
    }

    override fun hashCode(): Int = arrayOf(product, description, discountAmount).contentHashCode()
}