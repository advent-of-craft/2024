package santamarket.model

import java.lang.Math.*

case class ReceiptItem(product: Product, quantity: Double, price: Double, totalPrice: Double):
  override def equals(obj: Any): Boolean = obj match
    case that: ReceiptItem =>
      abs(this.price - that.price) < 0.001 &&
        abs(this.totalPrice - that.totalPrice) < 0.001 &&
        abs(this.quantity - that.quantity) < 0.001 &&
        this.product == that.product
    case _ => false

  override def hashCode(): Int =
    (product, price, totalPrice, quantity).hashCode