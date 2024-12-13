package santamarket.model

interface SantamarketCatalog {
    fun addProduct(product: Product, price: Double)
    fun getUnitPrice(product: Product): Double
}
