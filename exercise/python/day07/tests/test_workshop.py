import unittest

from assertpy import assert_that

from production.workshop import Gift, Status, Workshop


class WorkshopTest(unittest.TestCase):
    def test_completing_a_gift_should_set_its_status_to_produced(self):
        workshop = Workshop()
        toy_name = "1 Super Nintendo"
        workshop.add_gift(Gift(toy_name))

        completed_gift = workshop.complete_gift(toy_name)

        assert_that(completed_gift).is_not_none()
        assert_that(completed_gift.status).is_equal_to(Status.PRODUCED)

    def test_completing_a_non_existing_gift_should_return_nothing(self):
        workshop = Workshop()
        completed_gift = workshop.complete_gift("UnExisting toy")
        assert_that(completed_gift).is_none()


if __name__ == "__main__":
    unittest.main()
