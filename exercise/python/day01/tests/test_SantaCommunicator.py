import unittest

from assertpy import assert_that

from communication.santa_communicator import SantaCommunicator
from tests.doubles.logger import TestLogger


class SantaCommunicatorTest(unittest.TestCase):
    def setUp(self):
        self.number_of_days_to_rest = 2
        self.number_of_day_before_christmas = 24
        self.logger = TestLogger()
        self.communicator = SantaCommunicator(self.number_of_days_to_rest)

    def test_compose_message(self):
        message = self.communicator.compose_message("Dasher", "North Pole", 5, self.number_of_day_before_christmas)
        assert_that(message).is_equal_to(
            "Dear Dasher, please return from North Pole in 17 day(s) to be ready and rest before Christmas."
        )

    def test_is_overdue_detect_overdue_reindeer(self):
        overdue = self.communicator.is_overdue(
            "Dasher", "North Pole", self.number_of_day_before_christmas,
            self.number_of_day_before_christmas, self.logger
        )
        assert_that(overdue).is_true()
        assert_that(self.logger.get_log()).is_equal_to("Overdue for Dasher located North Pole.")

    def test_is_overdue_return_false_when_not_overdue(self):
        overdue = self.communicator.is_overdue(
            "Dasher", "North Pole",
            self.number_of_day_before_christmas - self.number_of_days_to_rest - 1,
            self.number_of_day_before_christmas, self.logger
        )
        assert_that(overdue).is_false()


if __name__ == "__main__":
    unittest.main()
