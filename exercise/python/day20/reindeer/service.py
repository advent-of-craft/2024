from typing import List, Optional
from uuid import uuid4


class ReindeerColor:
    WHITE = 0
    BLACK = 1
    PURPLE = 2


class Reindeer:
    def __init__(self, id: str, name: str, color: int):
        self.id = id
        self.name = name
        self.color = color


class ReindeerErrorCode:
    NOT_FOUND = "NotFound"
    ALREADY_EXIST = "AlreadyExist"


class ReindeerService:
    def __init__(self):
        self.reindeer: List[Reindeer] = [
            Reindeer("40f9d24d-d3e0-4596-adc5-b4936ff84b19", "Petar", ReindeerColor.BLACK)
        ]

    def get(self, id: str) -> Optional[Reindeer]:
        for reindeer in self.reindeer:
            if reindeer.id == str(id):
                return reindeer
        return None

    def create(self, name: str, color: int) -> Optional[str]:
        for reindeer in self.reindeer:
            if reindeer.name == name:
                return ReindeerErrorCode.ALREADY_EXIST
        new_reindeer = Reindeer(str(uuid4()), name, color)
        self.reindeer.append(new_reindeer)
        return new_reindeer.id
