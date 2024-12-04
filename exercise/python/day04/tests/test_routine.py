# test_routine.py

import unittest

from assertpy import assert_that


class RoutineTest(unittest.TestCase):
    def test_start_routine_with_mocks(self):
        self.universe()

    def test_start_routine_with_manual_test_doubles(self):
        self.universe()

    @staticmethod
    def universe():
        assert_that(42).is_equal_to("Universe")


if __name__ == "__main__":
    unittest.main()
