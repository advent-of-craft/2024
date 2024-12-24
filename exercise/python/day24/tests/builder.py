from faker import Faker

from domain.toy import Toy
from tests.time import Time

fake = Faker()


class ToyBuilder:
    def __init__(self, stock: int):
        self.stock = stock

    @staticmethod
    def toys_in_stock(stock: int):
        return ToyBuilder(stock)

    def build(self):
        return Toy.create(lambda: Time.time_provider(), fake.word(), self.stock)
