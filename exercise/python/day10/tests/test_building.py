import os
import unittest

from assertpy import assert_that
from parameterized import parameterized

from delivery.building import Building


def load_instructions_from_file(file_name: str) -> str:
    file_path = os.path.join(os.path.dirname(__file__), file_name)
    with open(file_path, 'r') as file:
        return file.read()


class BuildingTests(unittest.TestCase):
    @parameterized.expand([
        ("1.txt", 0),
        ("2.txt", 3),
        ("3.txt", -1),
        ("4.txt", 53),
        ("5.txt", -3),
        ("6.txt", 2920)
    ])
    def test_return_floor_number_based_on_instructions(self, file_name, expected_floor):
        instructions = load_instructions_from_file(file_name)
        result = Building.which_floor(instructions)
        assert_that(result).is_equal_to(expected_floor)


if __name__ == "__main__":
    unittest.main()
