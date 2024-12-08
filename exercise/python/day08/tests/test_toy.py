import unittest

from assertpy import assert_that

from domain.toy import Toy, State
from service.toy_service import ToyProductionService
from tests.doubles import InMemoryToyRepository


class ToyProductionServiceTest(unittest.TestCase):
    def test_assign_toy_to_elf_should_pass_the_item_in_production(self):
        toy_name = "Train"
        repository = InMemoryToyRepository()
        service = ToyProductionService(repository)
        repository.save(Toy(toy_name, State.UNASSIGNED))

        service.assign_toy_to_elf(toy_name)

        toy = repository.find_by_name(toy_name)
        assert_that(toy).is_not_none()
        assert_that(toy.state).is_equal_to(State.IN_PRODUCTION)


if __name__ == "__main__":
    unittest.main()
