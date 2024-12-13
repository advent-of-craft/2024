from enum import Enum

class ProductUnit(Enum):
    KILO = 'kilo'
    EACH = 'each'

class Product:
    def __init__(self, name: str, unit: ProductUnit):
        self.name = name
        self.unit = unit

    def __eq__(self, other):
        if isinstance(other, Product):
            return self.name == other.name and self.unit == other.unit
        return False

    def __hash__(self):
        return hash((self.name, self.unit))

    def __repr__(self):
        return f"Product(name='{self.name}', unit={self.unit})"
