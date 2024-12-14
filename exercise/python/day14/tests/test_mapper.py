import unittest
from uuid import UUID

from assertpy import assert_that

from children.db2 import X5T78
from children.dtos import Gender
from children.mapper import map_x5t78_to_child


class ChildMapperTest(unittest.TestCase):

    def test_should_map_X5T78_to_Child_for_a_girl(self):
        db2_child = X5T78(
            id=str(UUID('12345678123456781234567812345678')),
            n1="Alice",
            n2="Marie",
            n3="Smith",
            city_of_birth_pc="Paradise",
            person_bd="19/03/2017",
            salutation="Girl",
            st_num="123",
            st_name="Sunny Street",
            st_c="Paradise",
            st_cid="99"
        )

        child = map_x5t78_to_child(db2_child)

        assert_that(child.id).is_equal_to(UUID('12345678123456781234567812345678'))
        assert_that(child.first_name).is_equal_to("Alice")
        assert_that(child.middle_name).is_equal_to("Marie")
        assert_that(child.last_name).is_equal_to("Smith")
        assert_that(child.birth_city).is_equal_to("Paradise")
        assert_that(child.birth_date).is_equal_to("19/03/2017")
        assert_that(child.gender).is_equal_to(Gender.Girl)
        assert_that(child.address.number).is_equal_to("123")
        assert_that(child.address.city).is_equal_to("Paradise")
        assert_that(child.address.street).is_equal_to("Sunny Street")
        assert_that(child.address.country_id).is_equal_to(99)

    def test_should_map_X5T78_to_Child_for_a_boy(self):
        db2_child = X5T78(
            id=str(UUID('12345678123456781234567812345678')),
            n1="Bob",
            n3="Brown",
            city_of_birth_pc="Paradise",
            person_bd="01/09/2021",
            salutation="Boy",
            st_num="9",
            st_name="Oak Street",
            st_c="Paradise",
            st_cid="98988"
        )

        child = map_x5t78_to_child(db2_child)

        assert_that(child.id).is_equal_to(UUID('12345678123456781234567812345678'))
        assert_that(child.first_name).is_equal_to("Bob")
        assert_that(child.middle_name).is_none()
        assert_that(child.last_name).is_equal_to("Brown")
        assert_that(child.birth_city).is_equal_to("Paradise")
        assert_that(child.birth_date).is_equal_to("01/09/2021")
        assert_that(child.gender).is_equal_to(Gender.Boy)
        assert_that(child.address.number).is_equal_to("9")
        assert_that(child.address.city).is_equal_to("Paradise")
        assert_that(child.address.street).is_equal_to("Oak Street")
        assert_that(child.address.country_id).is_equal_to(98988)


if __name__ == "__main__":
    unittest.main()
