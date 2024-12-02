from typing import Optional

MIN = 1
MAX = 100
FIZZBUZZ = 15
FIZZ = 3
BUZZ = 5


class FizzBuzz:
    @staticmethod
    def convert(input: int) -> Optional[str]:
        if FizzBuzz.is_out_of_range(input):
            return None
        else:
            return FizzBuzz.convert_safely(input)

    @staticmethod
    def convert_safely(input: int) -> str:
        if FizzBuzz.is_divisible_by(FIZZBUZZ, input):
            return "FizzBuzz"
        elif FizzBuzz.is_divisible_by(FIZZ, input):
            return "Fizz"
        elif FizzBuzz.is_divisible_by(BUZZ, input):
            return "Buzz"
        else:
            return str(input)

    @staticmethod
    def is_divisible_by(divisor: int, input: int) -> bool:
        return input % divisor == 0

    @staticmethod
    def is_out_of_range(input: int) -> bool:
        return input < MIN or input > MAX
