package santamarket.model

class FakeCatalog : SantamarketCatalog {
    private val products = mutableMapOf<String, Product>()
    private val prices = mutableMapOf<String, Double>()

    override fun addProduct(product: Product, price: Double) {
        products[product.name] = product
        prices[product.name] = price
    }

    override fun getUnitPrice(product: Product): Double {
        return prices[product.name] ?: error("Product not found in catalog")
    }
}