package santamarket.model

import scala.collection.mutable.ListBuffer

case class Receipt():
  private val items = ListBuffer[ReceiptItem]()
  private val discounts = ListBuffer[Discount]()

  def getTotalPrice: Double =
    items.map(_.totalPrice).sum + discounts.map(_.discountAmount).sum

  def addProduct(product: Product, quantity: Double, price: Double, totalPrice: Double): Unit =
    items += ReceiptItem(product, quantity, price, totalPrice)

  def getItems: List[ReceiptItem] = items.toList

  def addDiscount(discount: Discount): Unit =
    discounts += discount

  def getDiscounts: List[Discount] = discounts.toList

  override def equals(obj: Any): Boolean = obj match
    case that: Receipt =>
      this.items == that.items && this.discounts == that.discounts
    case _ => false

  override def hashCode(): Int = (items, discounts).hashCode
