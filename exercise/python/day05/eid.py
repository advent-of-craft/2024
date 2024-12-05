from enum import Enum

MIN_SN = 1
MAX_SN = 999


class ElvenSex(Enum):
    SLOUBI = 0
    GAGNA = 2
    CATACT = 3


class Eid:
    @staticmethod
    def generate(sex: ElvenSex, sn: int, birth_year: int) -> str:
        prefix = Eid.format_sex(sex) + Eid.format_birth_year(birth_year) + Eid.validate_and_format(sn)
        control_key = Eid.format_control_key(prefix)
        return prefix + control_key

    @staticmethod
    def format_sex(sex: ElvenSex) -> str:
        return {ElvenSex.SLOUBI: "1", ElvenSex.GAGNA: "2", ElvenSex.CATACT: "3"}[sex]

    @staticmethod
    def format_birth_year(birth_year: int):
        return "{:02d}".format(birth_year % 100)

    @staticmethod
    def validate_and_format(sn: int) -> str:
        if sn < MIN_SN or sn > MAX_SN:
            raise ValueError(f"sn {sn} is out of range [{MIN_SN}, {MAX_SN}]")
        return "{:03d}".format(sn)

    @staticmethod
    def format_control_key(prefix: str) -> str:
        return "{:02d}".format(97 - (int(prefix) % 97))
