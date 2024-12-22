from typing import Union

from eid.parsing_error import ParsingError


def parse_or_error(input: str, is_valid, factory, invalid_message: str) -> Union[ParsingError, object]:
    try:
        value = int(input)
        if is_valid(value):
            return factory(value)
    except ValueError:
        pass
    return ParsingError(invalid_message)
