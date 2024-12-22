from typing import Union

from eid.parsing import parse_or_error
from eid.parsing_error import ParsingError

PARSING_ERROR_MESSAGE = "Year should be positive and lt 100"


def is_valid(value):
    return 0 <= value <= 99


class Year:
    def __init__(self, value: int):
        if not is_valid(value):
            raise ValueError(PARSING_ERROR_MESSAGE)
        self.value = value

    def __str__(self):
        return f"{self.value:02}"


def parse_year(text: str) -> Union[ParsingError, Year]:
    return parse_or_error(text, lambda x: is_valid(x), Year, PARSING_ERROR_MESSAGE)
