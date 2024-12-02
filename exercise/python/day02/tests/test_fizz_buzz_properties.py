import unittest

from hypothesis import given, strategies as st

from games.fizz_buzz import FizzBuzz, MIN, MAX


class FizzBuzzProperties(unittest.TestCase):
    fizz_buzz_strings = ["Fizz", "Buzz", "FizzBuzz"]

    def valid_strings_for(self, x: int) -> list:
        return self.fizz_buzz_strings + [str(x)]

    @given(st.integers(min_value=MIN, max_value=MAX))
    def test_parse_returns_valid_string_for_numbers_between_1_and_100(self, x):
        self.assertIn(
            FizzBuzz.convert(x),
            self.valid_strings_for(x)
        )

    @given(st.integers().filter(lambda i: i < MIN or i > MAX))
    def test_parse_fails_for_numbers_out_of_range(self, x):
        self.assertIsNone(
            FizzBuzz.convert(x)
        )


if __name__ == "__main__":
    unittest.main()
