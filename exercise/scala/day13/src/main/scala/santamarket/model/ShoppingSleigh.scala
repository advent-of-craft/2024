package santamarket.model

import scala.collection.mutable.{ListBuffer, Map => MutableMap}

class ShoppingSleigh:
  private val items = ListBuffer[ProductQuantity]()
  private val productQuantities = MutableMap[Product, Double]()

  def getItems: List[ProductQuantity] = items.toList

  def addItem(product: Product): Unit = addItemQuantity(product, 1.0)

  def productQuantitiesMap: Map[Product, Double] = productQuantities.toMap

  def addItemQuantity(product: Product, quantity: Double): Unit =
    items += ProductQuantity(product, quantity)
    productQuantities.updateWith(product) {
      case Some(existing) => Some(existing + quantity)
      case None => Some(quantity)
    }

  def handleOffers(receipt: Receipt, offers: Map[Product, Offer], catalog: SantamarketCatalog): Unit =
    productQuantitiesMap.foreach { case (product, quantity) =>
      offers.get(product).foreach { offer =>
        val unitPrice = catalog.getUnitPrice(product)
        val quantityAsInt = quantity.toInt
        var discount: Option[Discount] = None
        offer.offerType match
          case SpecialOfferType.THREE_FOR_TWO =>
            if quantityAsInt > 2 then
              val numberOfXs = quantityAsInt / 3
              val discountAmount = quantity * unitPrice - ((numberOfXs * 2 * unitPrice) + quantityAsInt % 3 * unitPrice)
              discount = Some(Discount(product, "3 for 2", -discountAmount))

          case SpecialOfferType.TWO_FOR_AMOUNT if quantityAsInt >= 2 =>
            val x = 2
            val total = offer.argument * (quantityAsInt.toDouble / x) + quantityAsInt % 2 * unitPrice
            val discountAmount = unitPrice * quantity - total
            discount = Some(Discount(product, s"2 for ${offer.argument}", -discountAmount))

          case SpecialOfferType.FIVE_FOR_AMOUNT if quantityAsInt >= 5 =>
            val x = 5
            val numberOfXs = quantityAsInt / x
            val discountAmount = unitPrice * quantity - (offer.argument * numberOfXs + quantityAsInt % 5 * unitPrice)
            discount = Some(Discount(product, s"$x for ${offer.argument}", -discountAmount))

          case SpecialOfferType.TEN_PERCENT_DISCOUNT =>
            discount = Some(Discount(product, s"${offer.argument}% off", -quantity * unitPrice * offer.argument / 100.0))

        discount.foreach(receipt.addDiscount)
      }
    }