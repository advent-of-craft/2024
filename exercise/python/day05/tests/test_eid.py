import unittest

from assertpy import assert_that

from eid import Eid, ElvenSex


class EIDTest(unittest.TestCase):
    def test_eid(self):
        eid = Eid.generate(sex=ElvenSex.SLOUBI, birth_year=0, sn=1)
        assert_that(eid).is_equal_to("10000195")

    def test_eid_sloubi(self):
        eid = Eid.generate(ElvenSex.SLOUBI, birth_year=0, sn=1)
        assert_that(eid).starts_with("1")

    def test_eid_gagna(self):
        eid = Eid.generate(ElvenSex.GAGNA, birth_year=0, sn=1)
        assert_that(eid).starts_with("2")

    def test_eid_catact(self):
        eid = Eid.generate(ElvenSex.CATACT, birth_year=0, sn=1)
        assert_that(eid).starts_with("3")


if __name__ == "__main__":
    unittest.main()
