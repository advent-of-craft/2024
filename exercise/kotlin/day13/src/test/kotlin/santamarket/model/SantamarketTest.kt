package santamarket.model

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.doubles.plusOrMinus
import io.kotest.matchers.shouldBe

class SantamarketTest : StringSpec({
    "noDiscount" {
        val catalog = FakeCatalog()
        val teddyBear = Product("teddyBear", ProductUnit.EACH)
        val teddyBearPrice = 1.0
        catalog.addProduct(teddyBear, teddyBearPrice)

        val sleigh = ShoppingSleigh()
        val numberOfTeddyBears = 3
        sleigh.addItemQuantity(teddyBear, numberOfTeddyBears.toDouble())

        val elf = ChristmasElf(catalog)
        val receipt = elf.checksOutArticlesFrom(sleigh)

        val expectedTotalPrice = numberOfTeddyBears * teddyBearPrice
        val expectedReceiptItem =
            ReceiptItem(teddyBear, numberOfTeddyBears.toDouble(), teddyBearPrice, expectedTotalPrice)

        receipt.getTotalPrice() shouldBe (expectedTotalPrice plusOrMinus 0.001)
        receipt.getItems() shouldContainExactly listOf(expectedReceiptItem)
    }

    "tenPercentDiscount" {
        val catalog = FakeCatalog()
        val turkey = Product("turkey", ProductUnit.KILO)
        val turkeyPrice = 2.0
        catalog.addProduct(turkey, turkeyPrice)

        val sleigh = ShoppingSleigh()
        val turkeyQuantity = 2.0
        sleigh.addItemQuantity(turkey, turkeyQuantity)

        val elf = ChristmasElf(catalog)
        elf.addSpecialOffer(SpecialOfferType.TEN_PERCENT_DISCOUNT, turkey, 10.0)

        val receipt = elf.checksOutArticlesFrom(sleigh)

        val expectedNonDiscountedPrice = turkeyQuantity * turkeyPrice
        val expectedTotalPrice = expectedNonDiscountedPrice * 0.9
        val expectedReceiptItem = ReceiptItem(turkey, turkeyQuantity, turkeyPrice, expectedNonDiscountedPrice)
        val expectedDiscount = Discount(turkey, "10.0% off", expectedTotalPrice - expectedNonDiscountedPrice)

        receipt.getTotalPrice() shouldBe (expectedTotalPrice plusOrMinus 0.001)
        receipt.getItems() shouldContainExactly listOf(expectedReceiptItem)
        receipt.getDiscounts() shouldContainExactly listOf(expectedDiscount)
    }

    "threeForTwoDiscount" {
        val catalog = FakeCatalog()
        val teddyBear = Product("teddyBear", ProductUnit.EACH)
        val teddyBearPrice = 1.0
        catalog.addProduct(teddyBear, teddyBearPrice)

        val elf = ChristmasElf(catalog)
        elf.addSpecialOffer(SpecialOfferType.THREE_FOR_TWO, teddyBear, 0.0)

        val sleigh = ShoppingSleigh()
        val numberOfTeddyBears = 3
        sleigh.addItemQuantity(teddyBear, numberOfTeddyBears.toDouble())

        val receipt = elf.checksOutArticlesFrom(sleigh)

        val expectedNonDiscountedPrice = numberOfTeddyBears * teddyBearPrice
        val expectedTotalPrice = 2 * teddyBearPrice
        val expectedReceiptItem =
            ReceiptItem(teddyBear, numberOfTeddyBears.toDouble(), teddyBearPrice, expectedNonDiscountedPrice)
        val expectedDiscount = Discount(teddyBear, "3 for 2", expectedTotalPrice - expectedNonDiscountedPrice)

        receipt.getTotalPrice() shouldBe (expectedTotalPrice plusOrMinus 0.001)
        receipt.getItems() shouldContainExactly listOf(expectedReceiptItem)
        receipt.getDiscounts() shouldContainExactly listOf(expectedDiscount)
    }

    "twoForAmountDiscount" {
        val catalog = FakeCatalog()
        val teddyBear = Product("teddyBear", ProductUnit.EACH)
        val teddyBearPrice = 1.0
        catalog.addProduct(teddyBear, teddyBearPrice)

        val elf = ChristmasElf(catalog)
        val discountedPriceForTwoTeddyBears = 1.6
        elf.addSpecialOffer(SpecialOfferType.TWO_FOR_AMOUNT, teddyBear, discountedPriceForTwoTeddyBears)

        val sleigh = ShoppingSleigh()
        val numberOfTeddyBears = 2
        sleigh.addItemQuantity(teddyBear, numberOfTeddyBears.toDouble())

        val receipt = elf.checksOutArticlesFrom(sleigh)

        val expectedNonDiscountedPrice = numberOfTeddyBears * teddyBearPrice
        val expectedTotalPrice = discountedPriceForTwoTeddyBears
        val expectedReceiptItem =
            ReceiptItem(teddyBear, numberOfTeddyBears.toDouble(), teddyBearPrice, expectedNonDiscountedPrice)
        val expectedDiscount = Discount(
            teddyBear,
            "2 for $discountedPriceForTwoTeddyBears",
            expectedTotalPrice - expectedNonDiscountedPrice
        )

        receipt.getTotalPrice() shouldBe (expectedTotalPrice plusOrMinus 0.001)
        receipt.getItems() shouldContainExactly listOf(expectedReceiptItem)
        receipt.getDiscounts() shouldContainExactly listOf(expectedDiscount)
    }

    "fiveForAmountDiscount" {
        val catalog = FakeCatalog()
        val teddyBear = Product("teddyBear", ProductUnit.EACH)
        val teddyBearPrice = 1.0
        catalog.addProduct(teddyBear, teddyBearPrice)

        val elf = ChristmasElf(catalog)
        val discountedPriceForFiveTeddyBears = 4.0
        elf.addSpecialOffer(SpecialOfferType.FIVE_FOR_AMOUNT, teddyBear, discountedPriceForFiveTeddyBears)

        val sleigh = ShoppingSleigh()
        val numberOfTeddyBears = 6
        repeat(numberOfTeddyBears) {
            sleigh.addItem(teddyBear)
        }

        val receipt = elf.checksOutArticlesFrom(sleigh)

        val expectedNonDiscountedPrice = numberOfTeddyBears * teddyBearPrice
        val expectedTotalPrice = discountedPriceForFiveTeddyBears + teddyBearPrice
        val expectedReceiptItem = ReceiptItem(teddyBear, 1.0, teddyBearPrice, teddyBearPrice)
        val expectedDiscount = Discount(
            teddyBear,
            "5 for $discountedPriceForFiveTeddyBears",
            expectedTotalPrice - expectedNonDiscountedPrice
        )

        receipt.getTotalPrice() shouldBe (expectedTotalPrice plusOrMinus 0.001)
        receipt.getItems() shouldContainExactly List(numberOfTeddyBears) { expectedReceiptItem }
        receipt.getDiscounts() shouldContainExactly listOf(expectedDiscount)
    }

    "twoDifferentItemsInSleighWithDiscountOnOneOfThem" {
        val catalog = FakeCatalog()
        val teddyBear = Product("teddyBear", ProductUnit.EACH)
        val teddyBearPrice = 1.0
        catalog.addProduct(teddyBear, teddyBearPrice)

        val turkey = Product("turkey", ProductUnit.KILO)
        val turkeyPrice = 2.0
        catalog.addProduct(turkey, turkeyPrice)

        val elf = ChristmasElf(catalog)
        elf.addSpecialOffer(SpecialOfferType.TEN_PERCENT_DISCOUNT, teddyBear, 10.0)

        val sleigh = ShoppingSleigh()
        val numberOfTeddyBears = 3
        sleigh.addItemQuantity(teddyBear, numberOfTeddyBears.toDouble())
        val turkeyQuantity = 1.5
        sleigh.addItemQuantity(turkey, turkeyQuantity)

        val receipt = elf.checksOutArticlesFrom(sleigh)

        val expectedNonDiscountedTeddyBearPrice = numberOfTeddyBears * teddyBearPrice
        val teddyBearReceiptItem =
            ReceiptItem(teddyBear, numberOfTeddyBears.toDouble(), teddyBearPrice, expectedNonDiscountedTeddyBearPrice)
        val totalTurkeyPrice = turkeyPrice * turkeyQuantity
        val turkeyReceiptItem = ReceiptItem(turkey, turkeyQuantity, turkeyPrice, totalTurkeyPrice)
        val expectedTotalTeddyBearPrice = expectedNonDiscountedTeddyBearPrice * 0.9
        val expectedTotalPrice = expectedTotalTeddyBearPrice + totalTurkeyPrice
        val expectedDiscount =
            Discount(teddyBear, "10.0% off", expectedTotalTeddyBearPrice - expectedNonDiscountedTeddyBearPrice)

        receipt.getTotalPrice() shouldBe (expectedTotalPrice plusOrMinus 0.001)
        receipt.getItems() shouldContainExactly listOf(teddyBearReceiptItem, turkeyReceiptItem)
        receipt.getDiscounts() shouldContainExactly listOf(expectedDiscount)
    }
})
