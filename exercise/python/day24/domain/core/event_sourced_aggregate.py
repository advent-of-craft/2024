from datetime import datetime
from typing import Callable

from domain.core.aggregate import Aggregate
from domain.core.event import Event
from domain.core.registered_routes import RegisteredRoutes


class EventSourcedAggregate(Aggregate):
    def __init__(self, time_provider: Callable[[], datetime]):
        super().__init__()
        self.time_provider = time_provider
        self.registered_routes = RegisteredRoutes()

    def apply_event(self, event: Event):
        self.registered_routes.dispatch(event)
        self.version += 1

    def raise_event(self, event: Event):
        self.apply_event(event)
        self.uncommitted_events.append(event)
