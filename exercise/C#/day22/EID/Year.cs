using LanguageExt;

namespace EID
{
    public readonly struct Year : IEquatable<Year>
    {
        private readonly int _value;
        private Year(int value) => _value = value;

        public static explicit operator Year(int value) =>
            Parse(value.ToString())
                .Match(y => y, err => throw new InvalidCastException(err.Reason));

        public static Either<ParsingError, Year> Parse(string potentialYear)
            => potentialYear.ToInt()
                .Filter(x => x is >= 0 and <= 99)
                .Map(year => new Year(year))
                .ToEither(new ParsingError("year should be between 0 and 99"));

        public override string ToString() => $"{_value:D2}";
        public bool Equals(Year other) => _value == other._value;
        public override bool Equals(object? obj) => obj is Year other && Equals(other);
        public override int GetHashCode() => _value;
        public static bool operator ==(Year left, Year right) => left.Equals(right);
        public static bool operator !=(Year left, Year right) => !(left == right);
    }
}