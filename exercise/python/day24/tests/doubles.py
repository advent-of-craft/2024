import uuid
from typing import Dict, List, Optional

from domain.core.event import Event
from domain.toy import Toy


class InMemoryToyRepository:
    def __init__(self):
        self.toys: Dict[uuid.UUID, Toy] = {}
        self.raised_events: List[Event] = []

    def find_by_name(self, toy_name: str) -> Optional[Toy]:
        return next((toy for toy in self.toys.values() if toy.name == toy_name), None)

    def find_by_id(self, toy_id: uuid.UUID) -> Optional[Toy]:
        return self.toys.get(toy_id)

    def save(self, aggregate: Toy):
        self.raised_events.clear()
        self.toys[aggregate.id] = aggregate
        self.raised_events.extend(aggregate.get_uncommitted_events())
        aggregate.clear_uncommitted_events()

    def get_raised_events(self) -> List[Event]:
        return self.raised_events
