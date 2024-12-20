using FluentAssertions;
using Xunit;
using static Travel.SantaTravelCalculator;

namespace Travel.Tests
{
    public class CalculatorTests
    {
        [Theory]
        [InlineData(1, 1)]
        [InlineData(2, 3)]
        [InlineData(5, 31)]
        [InlineData(10, 1023)]
        [InlineData(20, 1048575)]
        [InlineData(30, 1073741823)]
        [InlineData(50, 1_125_899_906_842_623)]
        public void Should_Calculate_The_DistanceFor(int numberOfReindeers, long expectedDistance)
            => ShouldCalculateTheDistanceFor(numberOfReindeers, expectedDistance);

        private static void ShouldCalculateTheDistanceFor(int numberOfReindeers, long expectedDistance)
        {
            CalculateTotalDistance(numberOfReindeers).Should().Be(expectedDistance);
            CalculateTotalDistanceWithLinQ(numberOfReindeers).Should().Be(expectedDistance);
            CalculateTotalDistanceWithBitWise(numberOfReindeers).Should().Be((ulong)expectedDistance);
        }
    }
}