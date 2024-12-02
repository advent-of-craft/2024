using FluentAssertions;

namespace Games.Tests;

public class RangeTests
{
    [Fact]
    public void Verify_Range_Is_Valid()
    {
        var range = Range.Create(1, 100);
        range.Min.Should().Be(1);
        range.Max.Should().Be(100);
    }
    
    [Fact]
    public void Verify_Range_Is_InValid()
    {
        var act = () => Range.Create(100, 1);
        act.Should()
            .Throw<ArgumentException>()
            .WithMessage("The maximum value must be greater than the minimum value.");
    }
}