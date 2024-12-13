from model.discount import Discount
from model.special_offer_type import SpecialOfferType


class ShoppingSleigh:
    def __init__(self):
        self.items = []
        self.product_quantities = {}

    def get_items(self):
        return self.items

    def add_item(self, product):
        self.add_item_quantity(product, 1.0)

    def add_item_quantity(self, product, quantity):
        self.items.append((product, quantity))
        if product in self.product_quantities:
            self.product_quantities[product] += quantity
        else:
            self.product_quantities[product] = quantity

    def get_product_quantities(self):
        return self.product_quantities.copy()

    def handle_offers(self, receipt, offers, catalog):
        for product, quantity in self.product_quantities.items():
            if product in offers:
                offer = offers[product]
                unit_price = catalog.get_unit_price(product)
                quantity_as_int = int(quantity)
                discount = None
                x = 1

                if offer.offer_type == SpecialOfferType.THREE_FOR_TWO:
                    x = 3
                elif offer.offer_type == SpecialOfferType.TWO_FOR_AMOUNT:
                    x = 2
                    if quantity_as_int >= 2:
                        total = offer.argument * (quantity_as_int // x) + (quantity_as_int % 2) * unit_price
                        discount_amount = unit_price * quantity - total
                        discount = Discount(product, f"2 for {offer.argument}", -discount_amount)

                if offer.offer_type == SpecialOfferType.FIVE_FOR_AMOUNT:
                    x = 5

                number_of_xs = int(quantity_as_int / x)

                if offer.offer_type == SpecialOfferType.THREE_FOR_TWO and quantity_as_int > 2:
                    discount_amount = quantity * unit_price - ((number_of_xs * 2 * unit_price) + (quantity_as_int % 3) * unit_price)
                    discount = Discount(product, "3 for 2", -discount_amount)

                if offer.offer_type == SpecialOfferType.TEN_PERCENT_DISCOUNT:
                    discount = Discount(product, f"{offer.argument}% off", -quantity * unit_price * offer.argument / 100.0)

                if offer.offer_type == SpecialOfferType.FIVE_FOR_AMOUNT and quantity_as_int >= 5:
                    discount_amount = unit_price * quantity - (offer.argument * number_of_xs + (quantity_as_int % 5) * unit_price)
                    discount = Discount(product, f"5 for {offer.argument}", -discount_amount)

                if discount is not None:
                    receipt.add_discount(discount)
