import uuid
from typing import List

from domain.core.event import Event


class Aggregate:
    def __init__(self):
        self.id = uuid.uuid4()
        self.version = 0
        self.uncommitted_events: List[Event] = []

    def apply_event(self, event: Event):
        raise NotImplementedError

    def get_uncommitted_events(self) -> List[Event]:
        return self.uncommitted_events

    def clear_uncommitted_events(self):
        self.uncommitted_events = []
