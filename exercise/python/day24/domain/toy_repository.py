import uuid
from typing import Optional

from domain.toy import Toy


class ToyRepository:
    def find_by_name(self, toy_name: str) -> Optional[Toy]:
        raise NotImplementedError

    def find_by_id(self, toy_id: uuid.UUID) -> Optional[Toy]:
        raise NotImplementedError

    def save(self, toy: Toy):
        raise NotImplementedError
