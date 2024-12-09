from enum import Enum


class Behavior(Enum):
    NAUGHTY = "NAUGHTY"
    NICE = "NICE"


class Priority(Enum):
    DREAM = "DREAM"
    NICE_TO_HAVE = "NICE_TO_HAVE"


class GiftRequest:
    def __init__(self, gift_name: str, is_feasible: bool, priority: Priority):
        self.gift_name = gift_name
        self.is_feasible = is_feasible
        self.priority = priority


class Child:
    def __init__(self, first_name: str, last_name: str, behavior: Behavior, gift_request: GiftRequest):
        self.first_name = first_name
        self.last_name = last_name
        self.behavior = behavior
        self.gift_request = gift_request


class SantaService:
    @staticmethod
    def evaluate_request(child: Child) -> bool:
        return child.behavior == Behavior.NICE and child.gift_request.is_feasible
