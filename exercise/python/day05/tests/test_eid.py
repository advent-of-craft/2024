import unittest

from assertpy import assert_that

from eid import Eid, ElvenSex


class EIDTest(unittest.TestCase):
    def test_eid(self):
        eid = Eid.generate(sex=ElvenSex.SLOUBI, birth_year=0, sn=1)
        assert_that(eid).is_equal_to("10000195")


if __name__ == "__main__":
    unittest.main()
