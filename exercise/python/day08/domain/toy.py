from enum import Enum


class State(Enum):
    UNASSIGNED = "UNASSIGNED"
    IN_PRODUCTION = "IN_PRODUCTION"
    COMPLETED = "COMPLETED"


class Toy:
    def __init__(self, name: str, state: State):
        self.name = name
        self.state = state


class ToyRepository:
    def find_by_name(self, name: str) -> Toy:
        raise NotImplementedError

    def save(self, toy: Toy):
        raise NotImplementedError
