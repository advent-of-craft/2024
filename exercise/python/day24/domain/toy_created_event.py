import datetime
import uuid

from domain.core.event import Event
from domain.stock_unit import StockUnit


class ToyCreatedEvent(Event):
    def __init__(self, event_id: uuid.UUID, date: datetime, name: str, stock: StockUnit):
        super().__init__(event_id, date)
        self.name = name
        self.stock = stock
