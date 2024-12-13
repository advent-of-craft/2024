from model.santamarket_catalog import SantamarketCatalog


class FakeCatalog(SantamarketCatalog):
    def __init__(self):
        self.products = {}
        self.prices = {}

    def add_product(self, product, price: float):
        self.products[product.name] = product
        self.prices[product.name] = price

    def get_unit_price(self, product):
        return self.prices[product.name]
