using FluentAssertions;
using SantaMarket.Model;
using Xunit;

namespace SantaMarket.Tests
{
    public class SantamarketTest
    {
        [Fact]
        public void NoDiscount()
        {
            var catalog = new FakeCatalog();

            var teddyBear = new Product("teddyBear", ProductUnit.Each);
            const double teddyBearPrice = 1;
            catalog.AddProduct(teddyBear, teddyBearPrice);

            var sleigh = new ShoppingSleigh();
            const int numberOfTeddyBears = 3;
            sleigh.AddItemQuantity(teddyBear, numberOfTeddyBears);

            var elf = new ChristmasElf(catalog);
            var receipt = elf.ChecksOutArticlesFrom(sleigh);

            const double expectedTotalPrice = numberOfTeddyBears * teddyBearPrice;
            var expectedReceiptItem =
                new ReceiptItem(teddyBear, numberOfTeddyBears, teddyBearPrice, expectedTotalPrice);

            receipt.TotalPrice().Should().Be(expectedTotalPrice);
            receipt.Items().Should().ContainSingle()
                .Which.Should().BeEquivalentTo(expectedReceiptItem);
        }

        [Fact]
        public void TenPercentDiscount()
        {
            var catalog = new FakeCatalog();

            var turkey = new Product("turkey", ProductUnit.Kilo);
            const double turkeyPrice = 2.0;
            catalog.AddProduct(turkey, turkeyPrice);

            var sleigh = new ShoppingSleigh();
            double turkeyQuantity = 2;
            sleigh.AddItemQuantity(turkey, turkeyQuantity);

            var elf = new ChristmasElf(catalog);
            elf.AddSpecialOffer(SpecialOfferType.TenPercentDiscount, turkey, 10.0);

            var receipt = elf.ChecksOutArticlesFrom(sleigh);

            var expectedNonDiscountedPrice = turkeyQuantity * turkeyPrice;
            var expectedTotalPrice = expectedNonDiscountedPrice * 0.9;
            var expectedReceiptItem = new ReceiptItem(turkey, turkeyQuantity, turkeyPrice, expectedNonDiscountedPrice);
            var expectedDiscount = new Discount(turkey, "10% off", expectedTotalPrice - expectedNonDiscountedPrice);

            receipt.TotalPrice().Should().Be(expectedTotalPrice);
            receipt.Items().Should().ContainSingle().Which.Should().BeEquivalentTo(expectedReceiptItem);
            receipt.GetDiscounts().Should().ContainSingle().Which.Should().BeEquivalentTo(expectedDiscount);
        }

        [Fact]
        public void ThreeForTwoDiscount()
        {
            var catalog = new FakeCatalog();

            var teddyBear = new Product("teddyBear", ProductUnit.Each);
            const double teddyBearPrice = 1;
            catalog.AddProduct(teddyBear, teddyBearPrice);

            var elf = new ChristmasElf(catalog);
            elf.AddSpecialOffer(SpecialOfferType.ThreeForTwo, teddyBear, 0.0);

            var sleigh = new ShoppingSleigh();
            const int numberOfTeddyBears = 3;
            sleigh.AddItemQuantity(teddyBear, numberOfTeddyBears);

            var receipt = elf.ChecksOutArticlesFrom(sleigh);

            var expectedNonDiscountedPrice = numberOfTeddyBears * teddyBearPrice;
            const double expectedTotalPrice = 2 * teddyBearPrice;
            var expectedReceiptItem =
                new ReceiptItem(teddyBear, numberOfTeddyBears, teddyBearPrice, expectedNonDiscountedPrice);
            var expectedDiscount = new Discount(teddyBear, "3 for 2", expectedTotalPrice - expectedNonDiscountedPrice);

            receipt.TotalPrice().Should().Be(expectedTotalPrice);
            receipt.Items().Should().ContainSingle().Which.Should().BeEquivalentTo(expectedReceiptItem);
            receipt.GetDiscounts().Should().ContainSingle().Which.Should().BeEquivalentTo(expectedDiscount);
        }

        [Fact]
        public void TwoForAmountDiscount()
        {
            var catalog = new FakeCatalog();

            var teddyBear = new Product("teddyBear", ProductUnit.Each);
            const double teddyBearPrice = 1;
            catalog.AddProduct(teddyBear, teddyBearPrice);

            var elf = new ChristmasElf(catalog);
            const double discountedPriceForTwoTeddyBears = 1.6;
            elf.AddSpecialOffer(SpecialOfferType.TwoForAmount, teddyBear, discountedPriceForTwoTeddyBears);

            var sleigh = new ShoppingSleigh();
            const int numberOfTeddyBears = 2;
            sleigh.AddItemQuantity(teddyBear, numberOfTeddyBears);

            var receipt = elf.ChecksOutArticlesFrom(sleigh);

            const double expectedNonDiscountedPrice = numberOfTeddyBears * teddyBearPrice;
            var expectedReceiptItem =
                new ReceiptItem(teddyBear, numberOfTeddyBears, teddyBearPrice, expectedNonDiscountedPrice);
            var expectedDiscount = new Discount(teddyBear, "2 for " + discountedPriceForTwoTeddyBears,
                discountedPriceForTwoTeddyBears - expectedNonDiscountedPrice);

            receipt.TotalPrice().Should().Be(discountedPriceForTwoTeddyBears);
            receipt.Items().Should().ContainSingle().Which.Should().BeEquivalentTo(expectedReceiptItem);
            receipt.GetDiscounts().Should().ContainSingle().Which.Should().BeEquivalentTo(expectedDiscount);
        }

        [Fact]
        public void FiveForAmountDiscount()
        {
            var catalog = new FakeCatalog();

            var teddyBear = new Product("teddyBear", ProductUnit.Each);
            const double teddyBearPrice = 1;
            catalog.AddProduct(teddyBear, teddyBearPrice);

            var elf = new ChristmasElf(catalog);
            const double discountedPriceForFiveTeddyBears = 4;
            elf.AddSpecialOffer(SpecialOfferType.FiveForAmount, teddyBear, discountedPriceForFiveTeddyBears);

            var sleigh = new ShoppingSleigh();
            var numberOfTeddyBears = 6;
            for (var i = 0; i < numberOfTeddyBears; i++)
            {
                sleigh.AddItem(teddyBear);
            }

            var receipt = elf.ChecksOutArticlesFrom(sleigh);

            var expectedNonDiscountedPrice = numberOfTeddyBears * teddyBearPrice;
            const double expectedTotalPrice = discountedPriceForFiveTeddyBears + teddyBearPrice;
            var expectedReceiptItem = new ReceiptItem(teddyBear, 1, teddyBearPrice, teddyBearPrice);
            var expectedDiscount = new Discount(teddyBear, "5 for " + discountedPriceForFiveTeddyBears,
                expectedTotalPrice - expectedNonDiscountedPrice);

            receipt.TotalPrice().Should().Be(expectedTotalPrice);
            receipt.Items().Should().HaveCount(numberOfTeddyBears).And
                .OnlyContain(item => item.Equals(expectedReceiptItem));
            receipt.GetDiscounts().Should().ContainSingle().Which.Should().BeEquivalentTo(expectedDiscount);
        }
    }
}