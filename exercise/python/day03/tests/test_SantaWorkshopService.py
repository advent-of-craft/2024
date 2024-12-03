import unittest

from assertpy import assert_that

from preparation.Gift import Gift
from preparation.SantaWorkshopService import SantaWorkshopService


class SantaWorkshopServiceTest(unittest.TestCase):
    def setUp(self):
        self.service = SantaWorkshopService()

    def test_prepare_gift_with_valid_parameters(self):
        gift_name = "Bitzee"
        weight = 3.0
        color = "Purple"
        material = "Plastic"

        gift = self.service.prepare_gift(gift_name, weight, color, material)
        assert_that(gift).is_not_none()

    def test_retrieve_attribute_of_gift(self):
        gift_name = "Furby"
        weight = 1.0
        color = "Multi"
        material = "Cotton"

        gift = Gift(gift_name, weight, color, material)
        gift.add_attribute("recommendedAge", "3")

        assert_that(gift.get_recommended_age()).is_equal_to(3)

    def test_prepare_gift_too_heavy(self):
        gift_name = "Dog-E"
        weight = 6.0
        color = "White"
        material = "Metal"

        with self.assertRaises(ValueError) as context:
            self.service.prepare_gift(gift_name, weight, color, material)

        assert_that(str(context.exception)).is_equal_to("Gift is too heavy for Santa's sleigh")


if __name__ == "__main__":
    unittest.main()
