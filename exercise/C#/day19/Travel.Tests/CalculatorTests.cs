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
        public void Should_Calculate_The_DistanceFor(int numberOfReindeers, int expectedDistance)
            => CalculateTotalDistanceRecursively(numberOfReindeers)
                .Should()
                .Be(expectedDistance);

        [Theory]
        [InlineData(32)]
        [InlineData(50)]
        public void Fail_For_Numbers_Greater_Than_32(int numberOfReindeers)
            // TODO find a way to support those values greater than 32
            // I expect a distance of 1 125 899 906 842 623 for 50 reindeers 
            => ((Func<int>?) (() => CalculateTotalDistanceRecursively(numberOfReindeers)))
                .Should()
                .Throw<OverflowException>();
    }
}