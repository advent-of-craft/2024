import unittest

from assertpy import assert_that

from eid import Eid, ElvenSex


class EIDTest(unittest.TestCase):

    def generate_and_check_eid(self, *args, **kwargs):
        eid = Eid.generate(*args, **kwargs)
        assert_that(eid).is_length(8)
        return eid

    def test_eid(self):
        eid = self.generate_and_check_eid(sex=ElvenSex.SLOUBI, birth_year=0, sn=1)
        assert_that(eid).is_equal_to("10000195")

    def test_eid_sloubi(self):
        eid = self.generate_and_check_eid(ElvenSex.SLOUBI, birth_year=0, sn=1)
        assert_that(eid).starts_with("1")

    def test_eid_gagna(self):
        eid = self.generate_and_check_eid(ElvenSex.GAGNA, birth_year=0, sn=1)
        assert_that(eid).starts_with("2")

    def test_eid_catact(self):
        eid = self.generate_and_check_eid(ElvenSex.CATACT, birth_year=0, sn=1)
        assert_that(eid).starts_with("3")

    def test_eid_birthyear(self):
        eid = self.generate_and_check_eid(ElvenSex.SLOUBI, birth_year=1950, sn=1)
        assert_that(eid[1:3]).is_equal_to("50")

    def test_eid_birthyear_has_leading_zero(self):
        eid = self.generate_and_check_eid(ElvenSex.SLOUBI, birth_year=1, sn=1)
        assert_that(eid[1:3]).is_equal_to("01")

    def test_sn(self):
        eid = self.generate_and_check_eid(ElvenSex.SLOUBI, birth_year=1, sn=123)
        assert_that(eid[3:6]).is_equal_to("123")

    def test_sn_has_leading_zero(self):
        eid = self.generate_and_check_eid(ElvenSex.SLOUBI, birth_year=1, sn=5)
        assert_that(eid[3:6]).is_equal_to("005")


if __name__ == "__main__":
    unittest.main()
