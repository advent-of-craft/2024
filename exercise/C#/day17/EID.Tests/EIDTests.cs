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
     
        private readonly Gen<string> _aValidEid
            = from Sex in Gen.Elements(1, 2, 3)
                from BirthYear in Gen.Choose(0, 99)    
                from SerialNumber in Gen.Choose(1, 999)
            select $"{ComputeControlKey(Sex, BirthYear, SerialNumber)}";

        private static string ComputeControlKey(int sex, int birthYear, int serialNumber)
        {
            var notValidatedEid = int.Parse($"{sex}{birthYear:D2}{serialNumber:D3}");
            var controlKey = 97 - notValidatedEid % 97;

            return $"{sex}{birthYear:D2}{serialNumber:D3}{controlKey:D2}";
        }

        [Property (MaxTest = 100)]
        public Property Parsing_A_Valid_EID_Should_Be_EID()
        {
            return Prop.ForAll(
                _aValidEid.ToArbitrary(),
                s =>
                    {
                        var anEid = EID.Parse(s);
                        anEid.Should().BeRight(x => x.Value.Should().Be(s));
                    });
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
                        anEid.Should().BeLeft(x => x.Message
                            .Should().Be("EID cannot be empty"));
                    });
            }

            [Property(MaxTest = 100)]
            public Property NotOnlyDigit_should_not_be_and_EID()
            {
                return Prop.ForAll(
                    _invalidEid
                        .Where(s => !string.IsNullOrWhiteSpace(s)
                                    && s.Length == 8
                                    && s.Any(c => !char.IsDigit(c)))
                        .ToArbitrary(),
                    s =>
                    {
                        var anEid = EID.Parse(s);
                        anEid.Should().BeLeft(x => x.Message
                            .Should().Be("EID must contain only digits"));
                    });
            }

            [Property (MaxTest = 100)]
            public Property NotValidLenght_should_not_be_and_EID()
            {
                return Prop.ForAll(
                    _invalidEid
                        .Where(s => !string.IsNullOrWhiteSpace(s)
                                    && s.Length < 8)
                        .ToArbitrary(),
                    s =>
                    {
                        var anEid = EID.Parse(s);
                        anEid.Should().BeLeft(x => x.Message
                            .Should().Be("EID length is invalid"));
                    });
            }
            
            
            private readonly Gen<string> _anInvalidSexForEid
                = from Sex in Gen.Elements(4, 5, 6, 7, 8, 9)
                from BirthYear in Gen.Choose(0, 99)    
                from SerialNumber in Gen.Choose(1, 999)
                select $"{ComputeControlKey(Sex, BirthYear, SerialNumber)}";

            [Property (MaxTest = 100)]
            public Property NotValidSex_should_not_be_and_EID()
            {
                return Prop.ForAll(
                    _anInvalidSexForEid.ToArbitrary(),
                    s =>
                    {
                        var anEid = EID.Parse(s);
                        anEid.Should().BeLeft(x => x.Message
                            .Should().Be("EID sex is invalid, must be 1, 2 or 3"));
                    });
            }

            private readonly Gen<string> _anInvalidSerialForEid
                = from Sex in Gen.Elements(1,2,3)
                    from BirthYear in Gen.Choose(0, 99)    
                    select $"{ComputeControlKey(Sex, BirthYear, 0)}";

            [Property (MaxTest = 100)]
            public Property NotValidSerialNumber_should_not_be_and_EID()
            {
                return Prop.ForAll(
                    _anInvalidSerialForEid.ToArbitrary(),
                    s =>
                    {
                        var anEid = EID.Parse(s);
                        anEid.Should().BeLeft(x => x.Message
                            .Should().Be("EID serial number must be between 001 and 999 digits"));
                    });
            }
            
            private readonly Gen<string> _anInvalidControlKeyForEid
                = from Sex in Gen.Elements(1, 2, 3)
                    from BirthYear in Gen.Choose(0, 99)    
                    from SerialNumber in Gen.Choose(1, 999)
                    //from ControlKey in Gen.Choose(1, 99) // might fail
                    from ControlKey in Gen.Elements(98, 99)
                select $"{Sex}{BirthYear:D2}{SerialNumber:D3}{ControlKey:D2}";

            [Property (MaxTest = 100)]
            public Property NotValidControlKey_should_not_be_and_EID()
            {
                return Prop.ForAll(
                    _anInvalidControlKeyForEid.ToArbitrary(),
                    s =>
                    {
                        var anEid = EID.Parse(s);
                        anEid.Should().BeLeft(x => x.Message
                            .Should().Be("EID key is invalid"));
                    });
            }
        }
    }
}