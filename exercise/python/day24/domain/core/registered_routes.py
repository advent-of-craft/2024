from typing import Dict, Callable

from domain.core.event import Event


class RegisteredRoutes:
    def __init__(self):
        self.routes: Dict[str, Callable[[Event], None]] = {}

    def dispatch(self, event: Event):
        if event.__class__.__name__ in self.routes:
            self.routes[event.__class__.__name__](event)

    def register(self, event_type: str, apply: Callable[[Event], None]):
        self.routes[event_type] = apply
