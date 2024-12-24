import datetime
import uuid

from domain.core.event import Event
from domain.stock_unit import StockUnit

class StockReducedEvent(Event):
    def __init__(self, event_id: uuid.UUID, date: datetime, toy_name: str, new_stock: StockUnit):
        super().__init__(event_id, date)
        self.toy_name = toy_name
        self.new_stock = new_stock
