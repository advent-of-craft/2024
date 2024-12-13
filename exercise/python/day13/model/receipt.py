from model.receipt_item import ReceiptItem


class Receipt:
    def __init__(self):
        self.items = []
        self.discounts = []

    def add_product(self, product, quantity: float, price: float, total_price: float):
        self.items.append(ReceiptItem(product, quantity, price, total_price))

    def add_discount(self, discount):
        self.discounts.append(discount)

    def total_price(self):
        total = sum(item.total_price for item in self.items)
        total += sum(discount.discount_amount for discount in self.discounts)
        return total

    def __repr__(self):
        return f"Receipt(items={self.items}, discounts={self.discounts})"
