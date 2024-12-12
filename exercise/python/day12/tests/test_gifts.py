import pytest
from assertpy import assert_that

from gifts.gifts import Toy, Child, Santa

Playstation = Toy("playstation")
Ball = Toy("ball")
Plush = Toy("plush")


def test_given_naughty_child_when_distributing_gifts_then_child_receives_third_choice():
    bobby = Child("bobby", "naughty")
    bobby.set_wishlist(Playstation, Plush, Ball)
    santa = Santa()
    santa.add_child(bobby)

    got = santa.choose_toy_for_child("bobby")

    assert_that(got).is_equal_to(Ball)


def test_given_nice_child_when_distributing_gifts_then_child_receives_second_choice():
    bobby = Child("bobby", "nice")
    bobby.set_wishlist(Playstation, Plush, Ball)
    santa = Santa()
    santa.add_child(bobby)

    got = santa.choose_toy_for_child("bobby")

    assert_that(got).is_equal_to(Plush)


def test_given_very_nice_child_when_distributing_gifts_then_child_receives_first_choice():
    bobby = Child("bobby", "very nice")
    bobby.set_wishlist(Playstation, Plush, Ball)
    santa = Santa()
    santa.add_child(bobby)

    got = santa.choose_toy_for_child("bobby")

    assert_that(got).is_equal_to(Playstation)


def test_given_non_existing_child_when_distributing_gifts_then_exception_thrown():
    santa = Santa()
    bobby = Child("bobby", "very nice")
    bobby.set_wishlist(Playstation, Plush, Ball)
    santa.add_child(bobby)

    with pytest.raises(ValueError) as excinfo:
        santa.choose_toy_for_child("alice")

    assert_that(str(excinfo.value)).is_equal_to("No such child found")
