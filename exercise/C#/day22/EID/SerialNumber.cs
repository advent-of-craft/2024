using LanguageExt;

namespace EID
{
    public readonly struct SerialNumber : IEquatable<SerialNumber>
    {
        private readonly int _value;
        public SerialNumber(int value) => _value = value;

        public static explicit operator SerialNumber(int value) =>
            Parse(value.ToString())
                .Match(s => s, err => throw new InvalidCastException(err.Reason));

        public static Either<ParsingError, SerialNumber> Parse(string potentialSerialNumber)
            => potentialSerialNumber.ToInt()
                .Filter(x => x is >= 1 and <= 999)
                .Map(number => new SerialNumber(number))
                .ToEither(new ParsingError("serial number should be between 1 and 999"));

        public override string ToString() => $"{_value:D3}";
        public bool Equals(SerialNumber other) => _value == other._value;
        public override bool Equals(object? obj) => obj is Year other && Equals(other);
        public override int GetHashCode() => _value;
        public static bool operator ==(SerialNumber left, SerialNumber right) => left.Equals(right);
        public static bool operator !=(SerialNumber left, SerialNumber right) => !(left == right);
    }
}