from assertpy import assert_that

from model.christmas_elf import ChristmasElf
from model.discount import Discount
from model.product import ProductUnit, Product
from model.receipt_item import ReceiptItem
from model.shopping_sleigh import ShoppingSleigh
from model.special_offer_type import SpecialOfferType
from tests.fake_catalog import FakeCatalog


def test_no_discount():
    catalog = FakeCatalog()
    teddy_bear = Product("teddyBear", ProductUnit.EACH)
    teddy_bear_price = 1.0
    catalog.add_product(teddy_bear, teddy_bear_price)

    sleigh = ShoppingSleigh()
    sleigh.add_item_quantity(teddy_bear, 3)

    elf = ChristmasElf(catalog)
    receipt = elf.checks_out_articles_from(sleigh)

    expected_total_price = 3 * teddy_bear_price
    expected_receipt_item = ReceiptItem(teddy_bear, 3, teddy_bear_price, expected_total_price)

    assert_that(receipt.total_price()).is_close_to(expected_total_price, 0.001)
    assert_that(receipt.items).contains(expected_receipt_item)


def test_ten_percent_discount():
    catalog = FakeCatalog()
    turkey = Product("turkey", ProductUnit.KILO)
    turkey_price = 2.0
    catalog.add_product(turkey, turkey_price)

    sleigh = ShoppingSleigh()
    sleigh.add_item_quantity(turkey, 2.0)

    elf = ChristmasElf(catalog)
    elf.add_special_offer(SpecialOfferType.TEN_PERCENT_DISCOUNT, turkey, 10.0)
    receipt = elf.checks_out_articles_from(sleigh)

    expected_non_discounted_price = 2.0 * turkey_price
    expected_total_price = expected_non_discounted_price * 0.9
    expected_receipt_item = ReceiptItem(turkey, 2.0, turkey_price, expected_non_discounted_price)
    expected_discount = Discount(turkey, "10.0% off", expected_total_price - expected_non_discounted_price)

    assert_that(receipt.total_price()).is_close_to(expected_total_price, 0.001)
    assert_that(receipt.discounts[0]).is_equal_to(expected_discount)
    assert_that(receipt.items[0]).is_equal_to(expected_receipt_item)


def test_three_for_two_discount():
    catalog = FakeCatalog()
    teddy_bear = Product("teddyBear", ProductUnit.EACH)
    teddy_bear_price = 1.0
    catalog.add_product(teddy_bear, teddy_bear_price)

    elf = ChristmasElf(catalog)
    elf.add_special_offer(SpecialOfferType.THREE_FOR_TWO, teddy_bear, 0.0)

    sleigh = ShoppingSleigh()
    sleigh.add_item_quantity(teddy_bear, 3)

    receipt = elf.checks_out_articles_from(sleigh)

    expected_non_discounted_price = 3 * teddy_bear_price
    expected_total_price = 2 * teddy_bear_price
    expected_receipt_item = ReceiptItem(teddy_bear, 3, teddy_bear_price, expected_non_discounted_price)
    expected_discount = Discount(teddy_bear, "3 for 2", expected_total_price - expected_non_discounted_price)

    assert_that(receipt.total_price()).is_close_to(expected_total_price, 0.001)
    assert_that(receipt.discounts).contains(expected_discount)
    assert_that(receipt.items[0]).is_equal_to(expected_receipt_item)


def test_two_for_amount_discount():
    catalog = FakeCatalog()
    teddy_bear = Product("teddyBear", ProductUnit.EACH)
    teddy_bear_price = 1.0
    catalog.add_product(teddy_bear, teddy_bear_price)

    elf = ChristmasElf(catalog)
    discounted_price_for_two_teddy_bears = 1.6
    elf.add_special_offer(SpecialOfferType.TWO_FOR_AMOUNT, teddy_bear, discounted_price_for_two_teddy_bears)

    sleigh = ShoppingSleigh()
    sleigh.add_item_quantity(teddy_bear, 2)

    receipt = elf.checks_out_articles_from(sleigh)

    expected_non_discounted_price = 2 * teddy_bear_price
    expected_total_price = discounted_price_for_two_teddy_bears
    expected_receipt_item = ReceiptItem(teddy_bear, 2, teddy_bear_price, expected_non_discounted_price)
    expected_discount = Discount(teddy_bear, f"2 for {discounted_price_for_two_teddy_bears}", expected_total_price - expected_non_discounted_price)

    assert_that(receipt.total_price()).is_close_to(expected_total_price, 0.001)
    assert_that(receipt.discounts).contains(expected_discount)
    assert_that(receipt.items[0]).is_equal_to(expected_receipt_item)


def test_five_for_amount_discount():
    catalog = FakeCatalog()
    teddy_bear = Product("teddyBear", ProductUnit.EACH)
    teddy_bear_price = 1.0
    catalog.add_product(teddy_bear, teddy_bear_price)

    elf = ChristmasElf(catalog)
    discounted_price_for_five_teddy_bears = 4.0
    elf.add_special_offer(SpecialOfferType.FIVE_FOR_AMOUNT, teddy_bear, discounted_price_for_five_teddy_bears)

    sleigh = ShoppingSleigh()
    for _ in range(6):
        sleigh.add_item(teddy_bear)

    receipt = elf.checks_out_articles_from(sleigh)

    expected_non_discounted_price = 6 * teddy_bear_price
    expected_total_price = discounted_price_for_five_teddy_bears + teddy_bear_price
    expected_discount = Discount(teddy_bear, f"5 for {discounted_price_for_five_teddy_bears}", expected_total_price - expected_non_discounted_price)

    assert_that(receipt.total_price()).is_close_to(expected_total_price, 0.001)
    assert_that(receipt.discounts).contains(expected_discount)


def test_discount_on_one_of_two_items_in_sleigh():
    catalog = FakeCatalog()
    teddy_bear = Product("teddyBear", ProductUnit.EACH)
    teddy_bear_price = 1.0
    catalog.add_product(teddy_bear, teddy_bear_price)

    turkey = Product("turkey", ProductUnit.KILO)
    turkey_price = 2.0
    catalog.add_product(turkey, turkey_price)

    elf = ChristmasElf(catalog)
    elf.add_special_offer(SpecialOfferType.TEN_PERCENT_DISCOUNT, teddy_bear, 10.0)

    sleigh = ShoppingSleigh()
    sleigh.add_item_quantity(teddy_bear, 3)
    sleigh.add_item_quantity(turkey, 1.5)

    receipt = elf.checks_out_articles_from(sleigh)

    expected_non_discounted_teddy_bear_price = 3 * teddy_bear_price
    expected_total_teddy_bear_price = expected_non_discounted_teddy_bear_price * 0.9
    total_turkey_price = turkey_price * 1.5
    expected_total_price = expected_total_teddy_bear_price + total_turkey_price
    expected_discount = Discount(teddy_bear, "10.0% off", expected_total_teddy_bear_price - expected_non_discounted_teddy_bear_price)

    assert_that(receipt.total_price()).is_close_to(expected_total_price, 0.001)
    assert_that(receipt.discounts).contains(expected_discount)
