from typing import Callable, List

from assertpy import assert_that
from hypothesis import given, strategies as st
from hypothesis.strategies import data

from eid.eid import EID, parse_eid
from eid.parsing_error import ParsingError
from eid.serial_number import SerialNumber
from eid.sex import Sex
from eid.year import Year


class Mutator:
    def __init__(self, name: str, func: Callable[[EID], st.SearchStrategy[str]]):
        self.name = name
        self.func = func

    def mutate(self, eid: EID) -> st.SearchStrategy[str]:
        return self.func(eid)


def eid_generator() -> st.SearchStrategy[EID]:
    return st.builds(
        EID,
        sex=st.sampled_from(list(Sex)),
        year=st.integers(min_value=0, max_value=99).map(Year),
        serial_number=st.integers(min_value=1, max_value=999).map(SerialNumber)
    )


def a_mutator(eid: EID) -> st.SearchStrategy[str]:
    return st.just("Implement this first mutator")


def simple_mutator(eid: EID) -> st.SearchStrategy[str]:
    mutated_eid = "Implement this first mutator"
    return st.just(mutated_eid)


mutators: List[Mutator] = [
    Mutator("simple mutator", simple_mutator)
]


@given(eid_generator())
def test_eid_round_tripping(valid_eid):
    parsed_eid = parse_eid(str(valid_eid))
    assert_that(str(parsed_eid)).is_equal_to(str(valid_eid))


@given(data())
def test_invalid_eid_can_never_be_parsed(data):
    valid_eid = data.draw(eid_generator())
    mutator = data.draw(st.sampled_from(mutators))
    mutated_eid = data.draw(mutator.mutate(valid_eid))

    assert_that(parse_eid(mutated_eid)).is_instance_of(ParsingError)
