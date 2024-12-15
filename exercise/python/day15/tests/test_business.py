import pytest
from assertpy import assert_that

from santaChristmasList.Business import Factory, Inventory, WishList, Child, Gift, ManufacturedGift, Business


@pytest.fixture
def setup_data():
    factory = Factory()
    inventory = Inventory()
    wish_list = WishList()

    john = Child('John')
    toy = Gift('Toy')
    manufactured_gift = ManufacturedGift('123')

    return factory, inventory, wish_list, john, toy, manufactured_gift


def test_gift_should_be_loaded_into_sleigh(setup_data):
    factory, inventory, wish_list, john, toy, manufactured_gift = setup_data

    wish_list[john] = toy
    factory[toy] = manufactured_gift
    inventory[manufactured_gift.bar_code] = toy

    business = Business(factory, inventory, wish_list)
    sleigh = business.load_gifts_in_sleigh(john)

    assert_that(sleigh).contains_key(john)
    assert_that(sleigh[john]).is_equal_to("Gift: Toy has been loaded!")


def test_gift_should_not_be_loaded_given_child_is_not_on_wish_list(setup_data):
    factory, inventory, wish_list, john, toy, manufactured_gift = setup_data

    business = Business(factory, inventory, wish_list)
    sleigh = business.load_gifts_in_sleigh(john)

    assert_that(sleigh).does_not_contain_key(john)


def test_gift_should_not_be_loaded_given_toy_was_not_manufactured(setup_data):
    factory, inventory, wish_list, john, toy, manufactured_gift = setup_data

    wish_list[john] = toy

    business = Business(factory, inventory, wish_list)
    sleigh = business.load_gifts_in_sleigh(john)

    assert_that(sleigh).does_not_contain_key(john)


def test_gift_should_not_be_loaded_given_toy_was_misplaced(setup_data):
    factory, inventory, wish_list, john, toy, manufactured_gift = setup_data

    wish_list[john] = toy
    factory[toy] = manufactured_gift

    business = Business(factory, inventory, wish_list)
    sleigh = business.load_gifts_in_sleigh(john)

    assert_that(sleigh).does_not_contain_key(john)
