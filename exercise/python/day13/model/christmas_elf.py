from .offer import Offer
from .receipt import Receipt

class ChristmasElf:
    def __init__(self, catalog):
        self.catalog = catalog
        self.offers = {}

    def add_special_offer(self, offer_type, product, argument):
        self.offers[product] = Offer(offer_type, product, argument)

    def checks_out_articles_from(self, sleigh):
        receipt = Receipt()

        for product, quantity in sleigh.get_items():
            unit_price = self.catalog.get_unit_price(product)
            total_price = quantity * unit_price
            receipt.add_product(product, quantity, unit_price, total_price)

        sleigh.handle_offers(receipt, self.offers, self.catalog)

        return receipt
