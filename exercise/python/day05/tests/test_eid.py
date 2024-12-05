import unittest

from assertpy import assert_that


class EIDTest(unittest.TestCase):
    def test_eid(self):
        assert_that(42).is_equal_to("Universe")


if __name__ == "__main__":
    unittest.main()
