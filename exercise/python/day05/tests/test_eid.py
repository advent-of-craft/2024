import unittest

from assertpy import assert_that

from eid import Eid, ElvenSex


class EIDTest(unittest.TestCase):

    def generate_and_check_eid(self, *args, **kwargs):
        eid = Eid.generate(*args, **kwargs)
        assert_that(eid).is_length(8)
        try:
            int(eid)
        except ValueError:
            raise AssertionError(f"eid {eid} is not an number")
        return eid

    def test_eid_sloubi(self):
        eid = self.generate_and_check_eid(ElvenSex.SLOUBI, birth_year=0, sn=1)
        assert_that(eid).starts_with("1")

    def test_eid_gagna(self):
        eid = self.generate_and_check_eid(ElvenSex.GAGNA, birth_year=0, sn=1)
        assert_that(eid).starts_with("2")

    def test_eid_catact(self):
        eid = self.generate_and_check_eid(ElvenSex.CATACT, birth_year=0, sn=1)
        assert_that(eid).starts_with("3")

    def test_eid_sex_must_be_elven_sex(self):
        with self.assertRaises(ValueError):
            self.generate_and_check_eid(sex="MALE", birth_year=0, sn=1)

    def test_eid_birthyear(self):
        eid = self.generate_and_check_eid(ElvenSex.SLOUBI, birth_year=1950, sn=1)
        assert_that(eid[1:3]).is_equal_to("50")

    def test_eid_birthyear_has_leading_zero(self):
        eid = self.generate_and_check_eid(ElvenSex.SLOUBI, birth_year=1, sn=1)
        assert_that(eid[1:3]).is_equal_to("01")

    def test_eid_sn(self):
        eid = self.generate_and_check_eid(ElvenSex.SLOUBI, birth_year=1, sn=123)
        assert_that(eid[3:6]).is_equal_to("123")

    def test_eid_sn_has_leading_zero(self):
        eid = self.generate_and_check_eid(ElvenSex.SLOUBI, birth_year=1, sn=5)
        assert_that(eid[3:6]).is_equal_to("005")

    def test_eid_cannot_be_out_of_bound(self):
        with self.assertRaises(ValueError):
            self.generate_and_check_eid(ElvenSex.SLOUBI, birth_year=1, sn=1000)
        with self.assertRaises(ValueError):
            self.generate_and_check_eid(ElvenSex.SLOUBI, birth_year=1, sn=0)

    @staticmethod
    def check_eid_control_key_match_first_6_digits(eid):
        prefix = eid[:6]
        control_key = eid[6:]
        assert_that((int(prefix) + int(control_key)) % 97).is_equal_to(0)

    def test_eid_control_key(self):
        for birth_year in range(100):
            eid = self.generate_and_check_eid(sex=ElvenSex.SLOUBI, birth_year=birth_year, sn=1)
            self.check_eid_control_key_match_first_6_digits(eid)
        for sn in range(1, 999):
            eid = self.generate_and_check_eid(sex=ElvenSex.SLOUBI, birth_year=1, sn=sn)
            self.check_eid_control_key_match_first_6_digits(eid)
        for sex in ElvenSex:
            eid = self.generate_and_check_eid(sex=sex, birth_year=1, sn=1)
            self.check_eid_control_key_match_first_6_digits(eid)


if __name__ == "__main__":
    unittest.main()
