from enum import Enum
from typing import Union

from eid.parsing_error import ParsingError


class Sex(Enum):
    Sloubi = 1
    Gagna = 2
    Catact = 3


def parse_sex(potential_sex: str) -> Union[ParsingError, Sex]:
    if potential_sex == '1':
        return Sex.Sloubi
    elif potential_sex == '2':
        return Sex.Gagna
    elif potential_sex == '3':
        return Sex.Catact
    else:
        return ParsingError("Not a valid sex")
