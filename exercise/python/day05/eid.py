from enum import Enum


class ElvenSex(Enum):
    SLOUBI = 0
    GAGNA = 2
    CATACT = 3


class Eid:
    @staticmethod
    def generate(sex: ElvenSex, sn: int, birth_year: int) -> str:
        prefix = Eid.format_sex(sex) + Eid.format_birth_year(birth_year) + Eid.format_sn(sn)
        control_key = Eid.format_control_key(prefix)
        return prefix + control_key

    @staticmethod
    def format_sex(sex: ElvenSex) -> str:
        return {ElvenSex.SLOUBI: "1", ElvenSex.GAGNA: "2", ElvenSex.CATACT: "3"}[sex]

    @staticmethod
    def format_birth_year(birth_year: int):
        return "{:02d}".format(birth_year % 100)

    @staticmethod
    def format_sn(sn: int) -> str:
        return "{:03d}".format(sn)

    @staticmethod
    def format_control_key(prefix: str) -> str:
        return "95"
