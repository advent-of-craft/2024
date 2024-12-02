using System.Text;
using FluentAssertions;
using FluentAssertions.LanguageExt;
using FsCheck;
using FsCheck.Xunit;

namespace Games.Tests;

public class FizzBuzzTests
{
    private static readonly Dictionary<int, string> Mapping = new()
    {
        {3, "Fizz"},
        {5, "Buzz"},
        {7, "Whizz"},
        {11, "Bang" },
    };
    private static readonly Configuration Configuration = Configuration.Create(
        mapping: Mapping,
        range: Range.Create(1, 100)); 
    private static FizzBuzz FizzBuzz => new(configuration: Configuration);
        
    private static readonly string[] FizzBuzzStrings
        = ["Fizz", "Buzz", "FizzBuzz", "Whizz", "FizzWhizz", "BuzzWhizz", "Bang", "FizzBang", "BuzzBang", "WhizzBang"]; 
        
    [Theory]
    [InlineData(1, "1")]
    [InlineData(67, "67")]
    [InlineData(82, "82")]
    [InlineData(3, "Fizz")]
    [InlineData(66, "FizzBang")]
    [InlineData(99, "FizzBang")]
    [InlineData(5, "Buzz")]
    [InlineData(50, "Buzz")]
    [InlineData(85, "Buzz")]
    [InlineData(15, "FizzBuzz")]
    [InlineData(30, "FizzBuzz")]
    [InlineData(45, "FizzBuzz")]
    public void Returns_Number_Representation(int input, string expectedResult)
        => FizzBuzz.Convert(input)
            .Should()
            .BeSome(x => x.Should().Be(expectedResult));

    [Property]
    public Property Parse_Return_Valid_String_For_Numbers_Between_1_And_100()
        => Prop.ForAll(
            ValidInput(),
            IsConvertValid
        );
        
    private static Arbitrary<int> ValidInput()
        => Gen.Choose(Configuration.Min, Configuration.Max).ToArbitrary();

    private static bool IsConvertValid(int x)
        => FizzBuzz.Convert(x).Exists(s => ValidStringsFor(x).Contains(s));

    private static IEnumerable<string> ValidStringsFor(int x)
        => FizzBuzzStrings.Append(x.ToString());

    [Property]
    public Property ParseFailForNumbersOutOfRange()
        => Prop.ForAll(
            InvalidInput(),
            x => FizzBuzz.Convert(x).IsNone
        );

    private static Arbitrary<int> InvalidInput()
        => Gen.Choose(-10_000, 10_000)
            .ToArbitrary()
            .Filter(x => x < Configuration.Min || x > Configuration.Max);

    [Fact]
    public async Task AcceptanceTest()
    {
        var configuration = Configuration.Create(
            mapping: Mapping,
            range: Range.Create(1, 100));
        var sut = new FizzBuzz(configuration);

        var result = new StringBuilder();
        
        for (var i = 1; i <= 100; i++) {
            sut.Convert(i)
                .IfSome(v => result.Append($"{i}\t{v}\n"));
        }
        
        await Verify(result.ToString());
    }
}