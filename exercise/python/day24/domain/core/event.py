import datetime
import uuid


class Event:
    def __init__(self, event_id: uuid.UUID, date: datetime, version: int = 1):
        self.id = event_id
        self.date = date
        self.version = version
