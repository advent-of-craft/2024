import unittest

from assertpy import assert_that
from hypothesis import given
from hypothesis.strategies import text

from encryption_email.encryption import Encryption
from tests.utils import convert_key, convert_iv


class TestEncryption(unittest.TestCase):
    def setUp(self):
        key = convert_key('Advent Of Craft')
        iv = convert_iv('2024')
        self.encryption = Encryption(key, iv)

    def test_encrypt_string(self):
        (
            assert_that(self.encryption.encrypt('Unlock Your Potential with the Advent Of Craft Calendar!'))
            .is_equal_to('L7wht/YddOoTvYvrc+wFcZhtXNvZ2cHFxq9ND27h1Ovv/aWLxN8lWv1xMsguM/R4Yodk3rn9cppI+YarggtPjA==')
        )

    # It is a Property-Based test that checks the below property
    # for all x (x: valid string) -> decrypt(encrypt(x)) == x
    # I'm pretty sure we will talk about this concept during our Journey 🎅
    @given(text())
    def test_encrypt_decrypt_property(self, random_string):
        assert_that(
            self.encryption.decrypt(self.encryption.encrypt(random_string))
        ).is_equal_to(random_string)


if __name__ == '__main__':
    unittest.main()
