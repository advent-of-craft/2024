from enum import Enum


class ToyType(Enum):
    EDUCATIONAL = "EDUCATIONAL"
    FUN = "FUN"
    CREATIVE = "CREATIVE"


class Preparation:
    @staticmethod
    def prepare_gifts(number_of_gifts: int) -> str:
        if number_of_gifts <= 0:
            return "No gifts to prepare."
        elif number_of_gifts < 50:
            return "Elves will prepare the gifts."
        else:
            return "Santa will prepare the gifts."

    @staticmethod
    def categorize_gift(age: int) -> str:
        if age <= 2:
            return "Baby"
        elif age <= 5:
            return "Toddler"
        elif age <= 12:
            return "Child"
        else:
            return "Teen"

    @staticmethod
    def ensure_toy_balance(toy_type: ToyType, toys_count: int, total_toys: int) -> bool:
        type_percentage = toys_count / total_toys
        if toy_type == ToyType.EDUCATIONAL:
            return type_percentage >= 0.25
        elif toy_type == ToyType.FUN:
            return type_percentage >= 0.30
        elif toy_type == ToyType.CREATIVE:
            return type_percentage >= 0.20
        return False
