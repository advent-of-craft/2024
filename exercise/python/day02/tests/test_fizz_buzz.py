import unittest

from assertpy import assert_that

from games.fizz_buzz import FizzBuzz
from tests.ValidInput import ValidInput


class FizzBuzzTests(unittest.TestCase):
    def test_returns_its_numbers_representation(self):
        test_data = [
            ValidInput(1, "1"),
            ValidInput(67, "67"),
            ValidInput(82, "82"),
            ValidInput(3, "Fizz"),
            ValidInput(66, "Fizz"),
            ValidInput(99, "Fizz"),
            ValidInput(5, "Buzz"),
            ValidInput(50, "Buzz"),
            ValidInput(85, "Buzz"),
            ValidInput(15, "FizzBuzz"),
            ValidInput(30, "FizzBuzz"),
            ValidInput(45, "FizzBuzz")
        ]

        for data in test_data:
            with ((self.subTest(data=data))):
                assert_that(FizzBuzz.convert(data.input)
                            ).is_equal_to(data.expected_result)

    def test_fails_for_numbers_out_of_range(self):
        test_data = [0, -1, 101]

        for value in test_data:
            with self.subTest(value=value):
                assert_that(FizzBuzz.convert(value)).is_none()


if __name__ == "__main__":
    unittest.main()
