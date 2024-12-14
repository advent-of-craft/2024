package santamarket.model;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static santamarket.model.ReceiptMatcher.matches;

class SantamarketTest {
    @Test
    void noDiscount() {
        SantamarketCatalog catalog = new FakeCatalog();

        Product teddyBear = new Product("teddyBear", ProductUnit.EACH);
        double teddyBearPrice = 1;
        catalog.addProduct(teddyBear, teddyBearPrice);

        ShoppingSleigh sleigh = new ShoppingSleigh();
        int numberOfTeddyBears = 3;
        sleigh.addItemQuantity(teddyBear, numberOfTeddyBears);

        // ACT
        ChristmasElf elf = new ChristmasElf(catalog);
        Receipt receipt = elf.checksOutArticlesFrom(sleigh);

        // ASSERT
        double expectedTotalPrice = numberOfTeddyBears * teddyBearPrice;
        ReceiptItem expectedReceiptItem = new ReceiptItem(teddyBear, numberOfTeddyBears, teddyBearPrice, expectedTotalPrice);

        ReceiptMatcher matcher = matches(expectedTotalPrice, List.of(expectedReceiptItem));
        assertThat(receipt, matcher);
    }

    @Test
    void tenPercentDiscount() {
        SantamarketCatalog catalog = new FakeCatalog();

        Product turkey = new Product("turkey", ProductUnit.KILO);
        double turkeyPrice = 2.;
        catalog.addProduct(turkey, turkeyPrice);

        ShoppingSleigh sleigh = new ShoppingSleigh();
        double turkeyQuantity = 2;
        sleigh.addItemQuantity(turkey, turkeyQuantity);

        ChristmasElf elf = new ChristmasElf(catalog);
        elf.addSpecialOffer(SpecialOfferType.TEN_PERCENT_DISCOUNT, turkey, 10.0);

        // ACT
        Receipt receipt = elf.checksOutArticlesFrom(sleigh);

        // ASSERT
        double expectedNonDiscountedPrice = turkeyQuantity * turkeyPrice;
        double expectedTotalPrice = expectedNonDiscountedPrice * 0.9;
        ReceiptItem expectedReceiptItem = new ReceiptItem(turkey, turkeyQuantity, turkeyPrice, expectedNonDiscountedPrice);
        Discount expectedDiscount = new Discount(turkey, "10.0% off", expectedTotalPrice - expectedNonDiscountedPrice);
        ReceiptMatcher matcher = matches(expectedTotalPrice, List.of(expectedReceiptItem), List.of(expectedDiscount));
        assertThat(receipt, matcher);
    }

    @Test
    void threeForTwoDiscount() {
        SantamarketCatalog catalog = new FakeCatalog();

        Product teddyBear = new Product("teddyBear", ProductUnit.EACH);
        double teddyBearPrice = 1;
        catalog.addProduct(teddyBear, teddyBearPrice);

        ChristmasElf elf = new ChristmasElf(catalog);
        elf.addSpecialOffer(SpecialOfferType.THREE_FOR_TWO, teddyBear, 0.);

        ShoppingSleigh sleigh = new ShoppingSleigh();
        int numberOfTeddyBears = 3;
        sleigh.addItemQuantity(teddyBear, numberOfTeddyBears);

        // ACT
        Receipt receipt = elf.checksOutArticlesFrom(sleigh);

        // ASSERT
        double expectedNonDiscountedPrice = numberOfTeddyBears * teddyBearPrice;
        double expectedTotalPrice = 2 * teddyBearPrice;
        ReceiptItem expectedReceiptItem = new ReceiptItem(teddyBear, numberOfTeddyBears, teddyBearPrice, expectedNonDiscountedPrice);
        Discount expectedDiscount = new Discount(teddyBear, "3 for 2", expectedTotalPrice - expectedNonDiscountedPrice);
        ReceiptMatcher matcher = matches(expectedTotalPrice, List.of(expectedReceiptItem), List.of(expectedDiscount));
        assertThat(receipt, matcher);
    }

    @Test
    void twoForAmountDiscount() {
        SantamarketCatalog catalog = new FakeCatalog();

        Product teddyBear = new Product("teddyBear", ProductUnit.EACH);
        double teddyBearPrice = 1;
        catalog.addProduct(teddyBear, teddyBearPrice);

        ChristmasElf elf = new ChristmasElf(catalog);
        double discountedPriceForTwoTeddyBears = 1.6;
        elf.addSpecialOffer(SpecialOfferType.TWO_FOR_AMOUNT, teddyBear, discountedPriceForTwoTeddyBears);

        ShoppingSleigh sleigh = new ShoppingSleigh();
        int numberOfTeddyBears = 2;
        sleigh.addItemQuantity(teddyBear, numberOfTeddyBears);

        // ACT
        Receipt receipt = elf.checksOutArticlesFrom(sleigh);

        // ASSERT
        double expectedNonDiscountedPrice = numberOfTeddyBears * teddyBearPrice;
        double expectedTotalPrice = discountedPriceForTwoTeddyBears;
        ReceiptItem expectedReceiptItem = new ReceiptItem(teddyBear, numberOfTeddyBears, teddyBearPrice, expectedNonDiscountedPrice);
        Discount expectedDiscount = new Discount(teddyBear, "2 for " + discountedPriceForTwoTeddyBears, expectedTotalPrice - expectedNonDiscountedPrice);
        ReceiptMatcher matcher = matches(expectedTotalPrice, List.of(expectedReceiptItem), List.of(expectedDiscount));
        assertThat(receipt, matcher);
    }

    @Test
    void fiveForAmountDiscount() {
        SantamarketCatalog catalog = new FakeCatalog();

        Product teddyBear = new Product("teddyBear", ProductUnit.EACH);
        double teddyBearPrice = 1;
        catalog.addProduct(teddyBear, teddyBearPrice);

        ChristmasElf elf = new ChristmasElf(catalog);
        double discountedPriceForFiveTeddyBears = 4;
        elf.addSpecialOffer(SpecialOfferType.FIVE_FOR_AMOUNT, teddyBear, discountedPriceForFiveTeddyBears);

        ShoppingSleigh sleigh = new ShoppingSleigh();
        int numberOfTeddyBears = 6;
        for (int i = 0; i < numberOfTeddyBears; i++) {
            sleigh.addItem(teddyBear);
        }

        // ACT
        Receipt receipt = elf.checksOutArticlesFrom(sleigh);

        // ASSERT
        double expectedNonDiscountedPrice = numberOfTeddyBears * teddyBearPrice;
        double expectedTotalPrice = discountedPriceForFiveTeddyBears + teddyBearPrice;
        ReceiptItem expectedReceiptItem = new ReceiptItem(teddyBear, 1, teddyBearPrice, teddyBearPrice);
        Discount expectedDiscount = new Discount(teddyBear, "5 for " + discountedPriceForFiveTeddyBears, expectedTotalPrice - expectedNonDiscountedPrice);
        ReceiptMatcher matcher = matches(expectedTotalPrice, Collections.nCopies(numberOfTeddyBears, expectedReceiptItem), List.of(expectedDiscount));
        assertThat(receipt, matcher);
    }

    @Test
    void twoDifferentItemsInSleighWithDiscountOnOneOfThem() {
        SantamarketCatalog catalog = new FakeCatalog();

        Product teddyBear = new Product("teddyBear", ProductUnit.EACH);
        double teddyBearPrice = 1;
        catalog.addProduct(teddyBear, teddyBearPrice);

        Product turkey = new Product("turkey", ProductUnit.KILO);
        double turkeyPrice = 2.;
        catalog.addProduct(turkey, turkeyPrice);

        ChristmasElf christmasElf = new ChristmasElf(catalog);
        christmasElf.addSpecialOffer(SpecialOfferType.TEN_PERCENT_DISCOUNT, teddyBear, 10.0);

        ShoppingSleigh sleigh = new ShoppingSleigh();
        int numberOfTeddyBears = 3;
        sleigh.addItemQuantity(teddyBear, numberOfTeddyBears);
        double turkeyQuantity = 1.5;
        sleigh.addItemQuantity(turkey, turkeyQuantity);

        // ACT
        Receipt receipt = christmasElf.checksOutArticlesFrom(sleigh);

        // ASSERT
        double expectedNonDiscountedTeddyBearPrice = numberOfTeddyBears * teddyBearPrice;
        ReceiptItem teddyBearReceiptItem = new ReceiptItem(teddyBear, numberOfTeddyBears, teddyBearPrice, expectedNonDiscountedTeddyBearPrice);
        double totalTurkeyPrice = turkeyPrice * turkeyQuantity;
        ReceiptItem turkeyReceiptItem = new ReceiptItem(turkey, turkeyQuantity, turkeyPrice, totalTurkeyPrice);
        double expectedTotalTeddyBearPrice = expectedNonDiscountedTeddyBearPrice * 0.9;
        double expectedTotalPrice = expectedTotalTeddyBearPrice + totalTurkeyPrice;
        Discount expectedDiscount = new Discount(teddyBear, "10.0% off", expectedTotalTeddyBearPrice - expectedNonDiscountedTeddyBearPrice);
        ReceiptMatcher matcher = matches(expectedTotalPrice, List.of(teddyBearReceiptItem, turkeyReceiptItem), List.of(expectedDiscount));
        assertThat(receipt, matcher);
    }

    @Test
    void twoForOne() {
        SantamarketCatalog catalog = new FakeCatalog();

        Product teddyBear = new Product("teddyBear", ProductUnit.EACH);
        double teddyBearPrice = 1;
        catalog.addProduct(teddyBear, teddyBearPrice);

        ChristmasElf christmasElf = new ChristmasElf(catalog);
        christmasElf.addSpecialOffer(SpecialOfferType.TWO_FOR_ONE, teddyBear, 0.);

        ShoppingSleigh sleigh = new ShoppingSleigh();
        int numberOfTeddyBears = 4;
        sleigh.addItemQuantity(teddyBear, numberOfTeddyBears);

        // ACT
        Receipt receipt = christmasElf.checksOutArticlesFrom(sleigh);

        // ASSERT
        double expectedNonDiscountedPrice = numberOfTeddyBears * teddyBearPrice;
        double expectedTotalPrice = 2 * teddyBearPrice;
        ReceiptItem expectedReceiptItem = new ReceiptItem(teddyBear, numberOfTeddyBears, teddyBearPrice, expectedNonDiscountedPrice);
        Discount expectedDiscount = new Discount(teddyBear, "2 for 1", expectedTotalPrice - expectedNonDiscountedPrice);
        ReceiptMatcher matcher = matches(expectedTotalPrice, List.of(expectedReceiptItem), List.of(expectedDiscount));
        assertThat(receipt, matcher);
    }
}