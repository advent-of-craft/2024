package santamarket.model

trait SantamarketCatalog:
  def addProduct(product: Product, price: Double): Unit
  def getUnitPrice(product: Product): Double
