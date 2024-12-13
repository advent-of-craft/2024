package santamarket.model

class ChristmasElf(val catalog: SantamarketCatalog):
  private val offers = scala.collection.mutable.Map[Product, Offer]()

  def addSpecialOffer(offerType: SpecialOfferType, product: Product, argument: Double): Unit =
    offers(product) = Offer(offerType, product, argument)

  def checksOutArticlesFrom(sleigh: ShoppingSleigh): Receipt =
    val receipt = Receipt()
    sleigh.getItems.foreach { pq =>
      val p = pq.product
      val quantity = pq.quantity
      val unitPrice = catalog.getUnitPrice(p)
      val price = quantity * unitPrice
      receipt.addProduct(p, quantity, unitPrice, price)
    }
    sleigh.handleOffers(receipt, offers.toMap, catalog)
    receipt
