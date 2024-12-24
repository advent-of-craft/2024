import unittest

from assertpy import assert_that
from faker import Faker
from hypothesis import given
from hypothesis.strategies import integers

from domain.core.error import Error
from domain.toy import Toy
from tests.time import Time

fake = Faker()
max_toys = 1_000_000_000


class CreateToyTests(unittest.TestCase):
    @given(integers(min_value=-max_toys, max_value=-1))
    def test_can_not_create_toy_with_invalid_stock(self, stock):
        toy = Toy.create(Time.time_provider, fake.name(), stock)
        assert_that(toy).is_instance_of(Error)

    @given(integers(min_value=0, max_value=max_toys))
    def test_can_create_toy_with_valid_stock(self, stock):
        toy = Toy.create(Time.time_provider, fake.name(), stock)
        assert_that(toy).is_instance_of(Toy)
