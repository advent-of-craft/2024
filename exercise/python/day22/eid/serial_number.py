from typing import Union

from eid.parsing import parse_or_error
from eid.parsing_error import ParsingError

PARSING_ERROR_MESSAGE = "Serial number should be gt 0 and lt 1000"


def is_valid(value):
    return 1 <= value <= 999


class SerialNumber:
    def __init__(self, value: int):
        if not is_valid(value):
            raise ValueError(PARSING_ERROR_MESSAGE)
        self.value = value

    def __str__(self):
        return f"{self.value:03}"


def parse_serial_number(text: str) -> Union[ParsingError, SerialNumber]:
    return parse_or_error(text, lambda x: is_valid(x), SerialNumber, PARSING_ERROR_MESSAGE)
