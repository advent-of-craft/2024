import uuid
from datetime import datetime
from typing import Union, Callable

from domain.core.error import Error
from domain.core.event_sourced_aggregate import EventSourcedAggregate
from domain.stock_reduced_event import StockReducedEvent
from domain.stock_unit import StockUnit, to_stock
from domain.toy_created_event import ToyCreatedEvent


class Toy(EventSourcedAggregate):
    def __init__(self, time_provider: Callable[[], datetime], name: str, stock: StockUnit):
        super().__init__(time_provider)
        self.name = name
        self.stock = stock
        self.raise_event(ToyCreatedEvent(uuid.uuid4(), self.time_provider(), name, stock))
        self.register_routes()

    @staticmethod
    def create(time_provider: Callable[[], datetime], name: str, stock: int) -> Union[Error, 'Toy']:
        stock_unit = to_stock(stock)
        if isinstance(stock_unit, Error):
            return stock_unit

        return Toy(time_provider, name, stock_unit)

    def reduce_stock(self) -> Union[Error, 'Toy']:
        if not self.stock.is_supplied():
            return Error(f"No more {self.name} in stock")

        new_stock = self.stock.decrease()
        self.raise_event(StockReducedEvent(self.id, self.time_provider(), self.name, new_stock))

        return self

    def register_routes(self):
        self.registered_routes.register(ToyCreatedEvent.__name__, self.apply_toy_created)
        self.registered_routes.register(StockReducedEvent.__name__, self.apply_stock_reduced)

    def apply_toy_created(self, event: ToyCreatedEvent):
        self.id = event.id
        self.name = event.name
        self.stock = event.stock

    def apply_stock_reduced(self, event: StockReducedEvent):
        self.stock = event.new_stock
