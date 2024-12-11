import unittest

from assertpy import assert_that
from parameterized import parameterized

from christmas.building import Preparation, ToyType


class PreparationTests(unittest.TestCase):

    @parameterized.expand([
        (-1, "No gifts to prepare."),
        (0, "No gifts to prepare."),
        (1, "Elves will prepare the gifts."),
        (49, "Elves will prepare the gifts."),
        (50, "Santa will prepare the gifts.")
    ])
    def test_prepare_gifts_should_return_correct_preparation_message(self, number_of_gifts, expected):
        result = Preparation.prepare_gifts(number_of_gifts)
        assert_that(result).is_equal_to(expected)

    @parameterized.expand([
        (1, "Baby"),
        (3, "Toddler"),
        (6, "Child"),
        (13, "Teen")
    ])
    def test_categorize_gift_should_return_correct_category_based_on_age(self, age, expected_category):
        result = Preparation.categorize_gift(age)
        assert_that(result).is_equal_to(expected_category)

    @parameterized.expand([
        (ToyType.EDUCATIONAL, 25, 100, True),
        (ToyType.FUN, 30, 100, True),
        (ToyType.CREATIVE, 20, 100, True),
        (ToyType.EDUCATIONAL, 20, 100, False),
        (ToyType.FUN, 29, 100, False),
        (ToyType.CREATIVE, 15, 100, False)
    ])
    def test_ensure_toy_balance_should_return_correct_balance_check(self, toy_type, toys_count, total_toys, expected):
        result = Preparation.ensure_toy_balance(toy_type, toys_count, total_toys)
        assert_that(result).is_equal_to(expected)


if __name__ == "__main__":
    unittest.main()
