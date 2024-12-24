import unittest

from assertpy import assert_that
from faker import Faker

from domain.stock_reduced_event import StockReducedEvent
from domain.toy import Toy
from tests import time
from tests.builder import ToyBuilder
from tests.doubles import InMemoryToyRepository
from usecases.deliver_toy import DeliverToy
from usecases.toy_delivery_use_case import ToyDeliveryUseCase

fake = Faker()


class ToyDeliveryTests(unittest.TestCase):

    def setUp(self):
        self.toy_repository = InMemoryToyRepository()
        self.use_case = ToyDeliveryUseCase(self.toy_repository)

    def for_a_supplied_toy(self, stock: int) -> Toy:
        toy = ToyBuilder.toys_in_stock(stock).build()
        self.toy_repository.save(toy)

        return toy

    def assert_no_event_has_been_raised(self):
        assert_that(self.toy_repository.get_raised_events()).is_empty()

    def test_toy_and_update_stock(self):
        toy = self.for_a_supplied_toy(1)
        command = DeliverToy(fake.name(), toy.name)

        result = self.use_case.handle(command)

        assert_that(result).is_none()
        last_event = self.toy_repository.get_raised_events()[-1] if self.toy_repository.get_raised_events() else None

        assert_that(last_event).is_instance_of(StockReducedEvent)
        assert_that(toy.version).is_equal_to(2)

        if isinstance(last_event, StockReducedEvent):
            assert_that(last_event.id).is_equal_to(toy.id)
            assert_that(last_event.date).is_equal_to(time.Time.time_provider())
            assert_that(last_event.version).is_equal_to(1)
            assert_that(last_event.toy_name).is_equal_to(toy.name)
            assert_that(last_event.new_stock.value).is_equal_to(0)

    def test_toy_has_not_been_built(self):
        not_built_toy = "Not a Bike"
        command = DeliverToy(fake.name(), not_built_toy)

        result = self.use_case.handle(command)

        assert_that(result.message).is_equal_to(f"Oops we have a problem... we have not built the toy: {not_built_toy}")
        self.assert_no_event_has_been_raised()

    def test_toy_is_not_supplied_anymore(self):
        toy = self.for_a_supplied_toy(0)
        command = DeliverToy(fake.name(), toy.name)

        result = self.use_case.handle(command)

        assert_that(result.message).is_equal_to(f"No more {toy.name} in stock")
        self.assert_no_event_has_been_raised()
