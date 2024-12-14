from enum import Enum
from typing import Optional
from uuid import UUID


class Gender(Enum):
    Girl = "Girl"
    Boy = "Boy"


class Address:
    def __init__(self, number: str, street: str, city: str, country_id: int):
        self.number = number
        self.street = street
        self.city = city
        self.country_id = country_id


class Child:
    def __init__(self,
                 id: UUID,
                 first_name: Optional[str],
                 middle_name: Optional[str],
                 last_name: Optional[str],
                 birth_city: Optional[str],
                 birth_date: Optional[str],
                 gender: Gender,
                 address: Optional[Address]):
        self.id = id
        self.first_name = first_name
        self.middle_name = middle_name
        self.last_name = last_name
        self.birth_city = birth_city
        self.birth_date = birth_date
        self.gender = gender
        self.address = address
