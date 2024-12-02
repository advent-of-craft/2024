using FluentAssertions;

namespace Games.Tests;

public class ConfigurationTests
{
    [Fact]
    public void Verify_Not_Prime_Numbers()
    {
        var mapping = new Dictionary<int, string>()
        {
            {3, "Fizz"},
            {5, "Buzz"},
            {8, "Whizz"},
            {11, "Bang" },
        };
        var range = Range.Create(1, 100);

        var act = () => Configuration.Create(mapping, range);

        act.Should()
            .Throw<ArgumentException>()
            .WithMessage("All keys in the mapping must be prime numbers.");
    }
}