package santamarket.model

class ChristmasElf(private val catalog: SantamarketCatalog) {
    private val offers = mutableMapOf<Product, Offer>()

    fun addSpecialOffer(offerType: SpecialOfferType, product: Product, argument: Double) {
        offers[product] = Offer(offerType, product, argument)
    }

    fun checksOutArticlesFrom(sleigh: ShoppingSleigh): Receipt {
        val receipt = Receipt()
        val productQuantities = sleigh.getItems()

        productQuantities.forEach { pq ->
            val product = pq.product
            val quantity = pq.quantity
            val unitPrice = catalog.getUnitPrice(product)
            val price = quantity * unitPrice
            receipt.addProduct(product, quantity, unitPrice, price)
        }

        sleigh.handleOffers(receipt, offers, catalog)
        return receipt
    }
}