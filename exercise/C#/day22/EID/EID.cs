using LanguageExt;

namespace EID
{
    public readonly struct EID : IEquatable<EID>
    {
        private const int ValidLength = 8;

        private readonly Sex _sex;
        private readonly Year _year;
        private readonly SerialNumber _serialNumber;

        public EID(Sex sex, Year year, SerialNumber serialNumber)
        {
            _sex = sex;
            _year = year;
            _serialNumber = serialNumber;
        }

        // Override the default constructor for readonly struct generated at compile time...
        public EID()
        {
            _sex = Sex.Sloubi;
            _year = (Year) 1;
            _serialNumber = (SerialNumber) 1;
        }

        public static Either<ParsingError, EID> Parse(string potentialEID)
            => (from sex in SexParser.Parse(potentialEID[0])
                    from year in Year.Parse(potentialEID[1..3])
                    from serialNumber in SerialNumber.Parse(potentialEID[3..6])
                    select new {EID = new EID(sex, year, serialNumber), Key = potentialEID[6..8]})
                .Bind(parsedEID => CheckKey(parsedEID.Key, parsedEID.EID));

        private static Either<ParsingError, EID> CheckKey(string potentialKey, EID eid)
            => potentialKey.ToInt()
                .Filter(parsedKey => parsedKey == eid.Key())
                .Map(_ => eid)
                .ToEither(new ParsingError("invalid key"));

        private string StringWithoutKey() => $"{(int) _sex}{_year}{_serialNumber}";

        private int Key()
            => StringWithoutKey()
                .ToInt()
                .Map(x => 97 - x % 97)
                .IfNone(0);

        public bool Equals(EID other) =>
            _sex == other._sex && _year == other._year && _serialNumber == other._serialNumber;

        public override bool Equals(object? obj) => obj is EID other && Equals(other);

        public override int GetHashCode() =>
            HashCode.Combine((int) _sex, _year, _serialNumber);

        public override string ToString() => StringWithoutKey() + $"{Key():D2}";

        public static bool operator ==(EID left, EID right) => left.Equals(right);

        public static bool operator !=(EID left, EID right) => !(left == right);
    }
}