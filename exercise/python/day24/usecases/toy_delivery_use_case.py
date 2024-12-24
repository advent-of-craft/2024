from typing import Union

from domain.core.error import Error
from domain.toy import Toy
from domain.toy_repository import ToyRepository
from usecases.deliver_toy import DeliverToy


class ToyDeliveryUseCase:
    def __init__(self, repository: ToyRepository):
        self.repository = repository

    def handle(self, deliver_toy: DeliverToy) -> Union[Error, None]:
        toy = self.repository.find_by_name(deliver_toy.desired_toy)
        if toy is None:
            return Error.an_error(f"Oops we have a problem... we have not built the toy: {deliver_toy.desired_toy}")
        result = self.reduce_stock(toy)
        if isinstance(result, Error):
            return result
        return None

    def reduce_stock(self, toy: Toy) -> Union[Error, Toy]:
        result = toy.reduce_stock()
        if isinstance(result, Toy):
            self.repository.save(result)
        else:
            self.repository.save(toy)
        return result
