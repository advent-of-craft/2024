import unittest

from assertpy import assert_that

from domain.gift import SantaService, Child, Behavior, GiftRequest, Priority


class SantaServiceTests(unittest.TestCase):
    def setUp(self):
        self.service = SantaService()

    def test_request_is_approved_for_nice_child_with_feasible_gift(self):
        nice_child = Child("Alice", "Thomas", Behavior.NICE, GiftRequest("Bicycle", True, Priority.NICE_TO_HAVE))
        result = self.service.evaluate_request(nice_child)
        assert_that(result).is_true()

    def test_request_is_denied_for_naughty_child(self):
        naughty_child = Child("Noa", "Thierry", Behavior.NAUGHTY, GiftRequest("SomeToy", True, Priority.DREAM))
        result = self.service.evaluate_request(naughty_child)
        assert_that(result).is_false()

    def test_request_is_denied_for_nice_child_with_infeasible_gift(self):
        nice_child_with_infeasible_gift = Child("Charlie", "Joie", Behavior.NICE,
                                                GiftRequest("AnotherToy", False, Priority.DREAM))
        result = self.service.evaluate_request(nice_child_with_infeasible_gift)
        assert_that(result).is_false()


if __name__ == "__main__":
    unittest.main()
