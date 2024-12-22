from typing import Union

from eid.parsing_error import ParsingError
from eid.serial_number import parse_serial_number
from eid.sex import parse_sex
from eid.year import parse_year


class EID:
    def __init__(self, sex, year, serial_number):
        self.sex = sex
        self.year = year
        self.serial_number = serial_number

    def check_key(self, potential_key: str) -> bool:
        key = int(potential_key)
        return self.key() == key

    def string_without_key(self) -> str:
        return f"{self.sex.value}{self.year}{self.serial_number}"

    def to_long(self) -> int:
        return int(f"{self.sex.value}{self.year}{self.serial_number}")

    def key(self) -> int:
        return 97 - (self.to_long() % 97)

    def __str__(self) -> str:
        return self.string_without_key() + f"{self.key():02}"


def parse_eid(potential_eid: str) -> Union[ParsingError, EID]:
    sex = parse_sex(potential_eid[0])

    if isinstance(sex, ParsingError):
        return sex

    year = parse_year(potential_eid[1:3])
    if isinstance(year, ParsingError):
        return year

    serial_number = parse_serial_number(potential_eid[3:6])
    if isinstance(serial_number, ParsingError):
        return serial_number

    eid = EID(sex, year, serial_number)

    if eid.check_key(potential_eid[6:]):
        return eid
    else:
        return ParsingError("Invalid key")
