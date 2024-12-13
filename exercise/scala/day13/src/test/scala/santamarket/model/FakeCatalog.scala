package santamarket.model

import scala.collection.mutable

class FakeCatalog extends SantamarketCatalog:
  private val products = mutable.Map[String, Product]()
  private val prices = mutable.Map[String, Double]()

  override def addProduct(product: Product, price: Double): Unit =
    products(product.name) = product
    prices(product.name) = price

  override def getUnitPrice(product: Product): Double =
    prices(product.name)