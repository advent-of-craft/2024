package santamarket.model

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class SantamarketSpec extends AnyFlatSpec with Matchers:

  def expectFloatCloseTo(actual: Double, expected: Double, tolerance: Double = 0.001): Unit =
    (actual - expected).abs should be <= tolerance

  "ChristmasElf" should "calculate total without discounts" in {
    val catalog = new FakeCatalog()
    val teddyBear = Product("teddyBear", ProductUnit.EACH)
    val teddyBearPrice = 1.0
    catalog.addProduct(teddyBear, teddyBearPrice)

    val sleigh = new ShoppingSleigh()
    sleigh.addItemQuantity(teddyBear, 3)

    val elf = ChristmasElf(catalog)
    val receipt = elf.checksOutArticlesFrom(sleigh)

    val expectedTotalPrice = 3 * teddyBearPrice
    val expectedReceiptItem = ReceiptItem(teddyBear, 3, teddyBearPrice, expectedTotalPrice)

    receipt.getTotalPrice should be(expectedTotalPrice)
    receipt.getItems.head should be(expectedReceiptItem)
  }

  it should "apply ten percent discount" in {
    val catalog = new FakeCatalog()
    val turkey = Product("turkey", ProductUnit.KILO)
    val turkeyPrice = 2.0
    catalog.addProduct(turkey, turkeyPrice)

    val sleigh = new ShoppingSleigh()
    sleigh.addItemQuantity(turkey, 2.0)

    val elf = ChristmasElf(catalog)
    elf.addSpecialOffer(SpecialOfferType.TEN_PERCENT_DISCOUNT, turkey, 10.0)
    val receipt = elf.checksOutArticlesFrom(sleigh)

    val expectedNonDiscountedPrice = 2.0 * turkeyPrice
    val expectedTotalPrice = expectedNonDiscountedPrice * 0.9
    val expectedReceiptItem = ReceiptItem(turkey, 2.0, turkeyPrice, expectedNonDiscountedPrice)
    val expectedDiscount = Discount(turkey, "10.0% off", expectedTotalPrice - expectedNonDiscountedPrice)

    expectFloatCloseTo(receipt.getTotalPrice, expectedTotalPrice)
    expectFloatCloseTo(receipt.getDiscounts.head.discountAmount, expectedDiscount.discountAmount)
    receipt.getDiscounts.head.description should be(expectedDiscount.description)
    receipt.getItems.head should be(expectedReceiptItem)
  }

  it should "apply three for two discount" in {
    val catalog = new FakeCatalog()
    val teddyBear = Product("teddyBear", ProductUnit.EACH)
    val teddyBearPrice = 1.0
    catalog.addProduct(teddyBear, teddyBearPrice)

    val elf = ChristmasElf(catalog)
    elf.addSpecialOffer(SpecialOfferType.THREE_FOR_TWO, teddyBear, 0.0)

    val sleigh = new ShoppingSleigh()
    sleigh.addItemQuantity(teddyBear, 3)

    val receipt = elf.checksOutArticlesFrom(sleigh)

    val expectedNonDiscountedPrice = 3 * teddyBearPrice
    val expectedTotalPrice = 2 * teddyBearPrice
    val expectedReceiptItem = ReceiptItem(teddyBear, 3, teddyBearPrice, expectedNonDiscountedPrice)
    val expectedDiscount = Discount(teddyBear, "3 for 2", expectedTotalPrice - expectedNonDiscountedPrice)

    expectFloatCloseTo(receipt.getTotalPrice, expectedTotalPrice)
    receipt.getDiscounts.head should be(expectedDiscount)
    receipt.getItems.head should be(expectedReceiptItem)
  }

  it should "apply two for amount discount" in {
    val catalog = new FakeCatalog()
    val teddyBear = Product("teddyBear", ProductUnit.EACH)
    val teddyBearPrice = 1.0
    catalog.addProduct(teddyBear, teddyBearPrice)

    val elf = ChristmasElf(catalog)
    val discountedPriceForTwoTeddyBears = 1.6
    elf.addSpecialOffer(SpecialOfferType.TWO_FOR_AMOUNT, teddyBear, discountedPriceForTwoTeddyBears)

    val sleigh = new ShoppingSleigh()
    sleigh.addItemQuantity(teddyBear, 2)

    val receipt = elf.checksOutArticlesFrom(sleigh)

    val expectedNonDiscountedPrice = 2 * teddyBearPrice
    val expectedTotalPrice = discountedPriceForTwoTeddyBears
    val expectedReceiptItem = ReceiptItem(teddyBear, 2, teddyBearPrice, expectedNonDiscountedPrice)
    val expectedDiscount = Discount(teddyBear, s"2 for $discountedPriceForTwoTeddyBears", expectedTotalPrice - expectedNonDiscountedPrice)

    expectFloatCloseTo(receipt.getTotalPrice, expectedTotalPrice)
    receipt.getDiscounts.head should be(expectedDiscount)
    receipt.getItems.head should be(expectedReceiptItem)
  }

  it should "apply five for amount discount" in {
    val catalog = new FakeCatalog()
    val teddyBear = Product("teddyBear", ProductUnit.EACH)
    val teddyBearPrice = 1.0
    catalog.addProduct(teddyBear, teddyBearPrice)

    val elf = ChristmasElf(catalog)
    val discountedPriceForFiveTeddyBears = 4.0
    elf.addSpecialOffer(SpecialOfferType.FIVE_FOR_AMOUNT, teddyBear, discountedPriceForFiveTeddyBears)

    val sleigh = new ShoppingSleigh()
    for _ <- 1 to 6 do sleigh.addItem(teddyBear)

    val receipt = elf.checksOutArticlesFrom(sleigh)

    val expectedNonDiscountedPrice = 6 * teddyBearPrice
    val expectedTotalPrice = discountedPriceForFiveTeddyBears + teddyBearPrice
    val expectedReceiptItem = ReceiptItem(teddyBear, 1, teddyBearPrice, teddyBearPrice)
    val expectedDiscount = Discount(teddyBear, s"5 for $discountedPriceForFiveTeddyBears", expectedTotalPrice - expectedNonDiscountedPrice)

    expectFloatCloseTo(receipt.getTotalPrice, expectedTotalPrice)
    receipt.getDiscounts.head should be(expectedDiscount)
    receipt.getItems.head should be(expectedReceiptItem)
  }

  it should "apply discount for one of two different items in sleigh" in {
    val catalog = new FakeCatalog()
    val teddyBear = Product("teddyBear", ProductUnit.EACH)
    val teddyBearPrice = 1.0
    catalog.addProduct(teddyBear, teddyBearPrice)

    val turkey = Product("turkey", ProductUnit.KILO)
    val turkeyPrice = 2.0
    catalog.addProduct(turkey, turkeyPrice)

    val elf = ChristmasElf(catalog)
    elf.addSpecialOffer(SpecialOfferType.TEN_PERCENT_DISCOUNT, teddyBear, 10.0)

    val sleigh = new ShoppingSleigh()
    sleigh.addItemQuantity(teddyBear, 3)
    sleigh.addItemQuantity(turkey, 1.5)

    val receipt = elf.checksOutArticlesFrom(sleigh)

    val expectedNonDiscountedTeddyBearPrice = 3 * teddyBearPrice
    val expectedTotalTeddyBearPrice = expectedNonDiscountedTeddyBearPrice * 0.9
    val totalTurkeyPrice = turkeyPrice * 1.5
    val expectedTotalPrice = expectedTotalTeddyBearPrice + totalTurkeyPrice

    val expectedDiscount = Discount(teddyBear, "10.0% off", expectedTotalTeddyBearPrice - expectedNonDiscountedTeddyBearPrice)

    expectFloatCloseTo(receipt.getTotalPrice, expectedTotalPrice)
    receipt.getDiscounts.head should be(expectedDiscount)
  }
