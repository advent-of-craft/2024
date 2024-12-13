package santamarket.model

case class Discount(product: Product, description: String, discountAmount: Double):
  override def equals(obj: Any): Boolean = obj match
    case that: Discount =>
      this.description == that.description &&
        Math.abs(this.discountAmount - that.discountAmount) < 0.001 &&
        this.product == that.product
    case _ => false

  override def hashCode(): Int =
    (description, discountAmount, product).hashCode