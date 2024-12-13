class ReceiptItem:
    def __init__(self, product, quantity: float, price: float, total_price: float):
        self.product = product
        self.quantity = quantity
        self.price = price
        self.total_price = total_price

    def __eq__(self, other):
        if isinstance(other, ReceiptItem):
            return (
                    self.product == other.product and
                    abs(self.price - other.price) < 0.001 and
                    abs(self.total_price - other.total_price) < 0.001 and
                    abs(self.quantity - other.quantity) < 0.001
            )
        return False

    def __repr__(self):
        return (f"ReceiptItem(product={self.product}, price={self.price}, "
                f"total_price={self.total_price}, quantity={self.quantity})")
