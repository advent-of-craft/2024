import unittest

from parameterized import parameterized

from travel.SantaTravelCalculator import SantaTravelCalculator


class CalculatorTests(unittest.TestCase):

    @parameterized.expand([
        (1, 1),
        (2, 3),
        (5, 31),
        (10, 1023),
        (20, 1048575),
        (30, 1073741823),
    ])
    def test_calculate_total_distance_correctly(self, number_of_reindeers, expected_distance):
        self.assertEqual(SantaTravelCalculator.calculate_total_distance_recursively(number_of_reindeers),
                         expected_distance)

    @parameterized.expand([
        (32,),
        (50,),
    ])
    def test_should_fail_for_numbers_greater_than_32(self, number_of_reindeers):
        with self.assertRaises(ArithmeticError) as context:
            SantaTravelCalculator.calculate_total_distance_recursively(number_of_reindeers)
        self.assertEqual(str(context.exception), "Integer overflow")


if __name__ == "__main__":
    unittest.main()
