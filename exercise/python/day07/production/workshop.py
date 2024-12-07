from enum import Enum
from typing import Optional


class Status(Enum):
    PRODUCING = "Producing"
    PRODUCED = "Produced"


class Gift:
    def __init__(self, name: str, status: Status = Status.PRODUCING):
        self.name = name
        self.status = status


class Workshop:
    def __init__(self):
        self.gifts = []

    def add_gift(self, gift: Gift):
        self.gifts.append(gift)

    def complete_gift(self, name: str) -> Optional[Gift]:
        for gift in self.gifts:
            if gift.name == name:
                gift.status = Status.PRODUCED
                return gift
        return None
