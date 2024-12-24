from typing import Union

from domain.core.error import Error


class StockUnit:
    def __init__(self, value: int):
        if value < 0:
            raise ValueError("A stock unit cannot be negative")
        self.value = value

    def is_supplied(self) -> bool:
        return self.value > 0

    def decrease(self) -> 'StockUnit':
        return StockUnit(self.value - 1)


def to_stock(stock: int) -> Union[Error, StockUnit]:
    if stock >= 0:
        return StockUnit(stock)
    else:
        return Error("A stock unit cannot be negative")
