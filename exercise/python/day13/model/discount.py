class Discount:
    def __init__(self, product, description: str, discount_amount: float):
        self.product = product
        self.description = description
        self.discount_amount = discount_amount

    def __eq__(self, other):
        if isinstance(other, Discount):
            return (
                    self.product == other.product and
                    self.description == other.description and
                    abs(self.discount_amount - other.discount_amount) < 0.001
            )
        return False

    def __repr__(self):
        return f"Discount(description='{self.description}', discount_amount={self.discount_amount}, product={self.product})"
