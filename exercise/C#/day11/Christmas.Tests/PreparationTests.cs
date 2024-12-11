using FluentAssertions;
using Xunit;
using static Christmas.ToyType;

namespace Christmas.Tests
{
    public class PreparationTests
    {
        [Theory]
        [InlineData(-1, "No gifts to prepare.")]
        [InlineData(0, "No gifts to prepare.")]
        [InlineData(1, "Elves will prepare the gifts.")]
        [InlineData(49, "Elves will prepare the gifts.")]
        [InlineData(50, "Santa will prepare the gifts.")]
        public void PrepareGifts(int numberOfGifts, string expected)
            => Preparation.PrepareGifts(numberOfGifts);

        [Theory]
        [InlineData(1, "Baby")]
        [InlineData(3, "Toddler")]
        [InlineData(6, "Child")]
        [InlineData(13, "Teen")]
        public void CategorizeGift(int age, string expectedCategory)
            => Preparation.CategorizeGift(age)
                .Should()
                .Be(expectedCategory);

        [Theory]
        [InlineData(Educational, 25, 100, true)]
        [InlineData(Fun, 30, 100, true)]
        [InlineData(Creative, 20, 100, true)]
        [InlineData(Educational, 20, 100, false)]
        [InlineData(Fun, 29, 100, false)]
        [InlineData(Creative, 15, 100, false)]
        public void EnsureToyBalance(ToyType toyType, int toysCount, int totalToys, bool expected)
            => Preparation.EnsureToyBalance(toyType, toysCount, totalToys)
                .Should()
                .Be(expected);
    }
}