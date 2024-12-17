using FluentAssertions.LanguageExt;
using FsCheck;
using FsCheck.Xunit;
using LanguageExt.UnsafeValueAccess;
using Property = FsCheck.Property;

namespace EID.Tests
{
    public class EIDTests
    {
        [Theory]
        [InlineData("19845606")]
        [InlineData("30600233")]
        [InlineData("29999922")]
        [InlineData("11111151")]
        [InlineData("19800767")]
        public void EidParse_Should_Return_Valid_Eid(string validEid)
        {
            var anEid = EID.Parse(validEid);
            anEid.Should().BeRight();
            anEid.ValueUnsafe().Value.Should().Be(validEid);
        }
        
        [Theory]
        [InlineData(null)] // String may be null (depending on the language used)
        [InlineData("")] // empty string
        [InlineData("2230")] // too short
        [InlineData("40000325")] // incorrect sex
        [InlineData("1ab14599")] // incorrect birth year
        [InlineData("19814x08")]// incorrect serial number
        [InlineData("19912378")] // incorrect control key
        public void EidParse_Should_Return_AnError(string invalidEid)
        {
            var anEid = EID.Parse(invalidEid);
            anEid.Should().BeLeft();
        }

        public class Failures
        {
            private readonly Gen<string> _invalidEid
                = Arb.Generate<string>(); 
            
            [Property (MaxTest = 100)]
            public Property Empty_should_not_be_and_EID()
            {
                return Prop.ForAll(
                    _invalidEid
                        .Where(string.IsNullOrWhiteSpace)
                        .ToArbitrary(),
                    s =>
                    {
                        var anEid = EID.Parse(s);
                        anEid.Should().BeLeft(x => x.Message.Should().Be("EID cannot be empty"));
                    });
            }
            
            [Property (MaxTest = 100)]
            public Property NotOnlyDigit_should_not_be_and_EID()
            {
                return Prop.ForAll(
                    _invalidEid
                        .Where(s => !string.IsNullOrWhiteSpace(s)
                                          && s.Any(c => !char.IsDigit(c)))
                        .ToArbitrary(),
                    s =>
                    {
                        var anEid = EID.Parse(s);
                        anEid.Should().BeLeft(x => x.Message.Should().Be("EID must contain only digits"));
                    });
            }
            
            [Property (MaxTest = 10)]
            public Property NotValidSex_should_not_be_and_EID()
            {
                return Prop.ForAll(
                    _invalidEid
                        .Where(s => !string.IsNullOrWhiteSpace(s)
                                    && s.All(c => char.IsDigit(c))
                                    && !"123".Contains(s[0]))
                        .ToArbitrary(),
                    s =>
                    {
                        var anEid = EID.Parse(s);
                        anEid.Should().BeLeft(x => x.Message.Should().Be("EID sex is invalid, must be 1, 2 or 3"));
                    });
            }
            
            /*[Property (MaxTest = 1)]
            public Property NotValidSerialNumber_should_not_be_and_EID()
            {
                return Prop.ForAll(
                    _invalidEid
                        .Where(s => !string.IsNullOrWhiteSpace(s)
                                    && s.All(c => char.IsDigit(c))
                                    && "123".Contains(s[0])
                                    && s.Length == 8
                                    && s.Substring(2, 3) == "000")
                        .ToArbitrary(),
                    s =>
                    {
                        var anEid = EID.Parse(s);
                        anEid.Should().BeLeft(x => x.Message.Should().Be("EID serial number must be between 001 and 999 digits"));
                    });
            }*/
        }
    }
}