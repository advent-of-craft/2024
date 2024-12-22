using LanguageExt;

namespace EID
{
    public enum Sex
    {
        Sloubi = 1,
        Gagna = 2,
        Catact = 3
    }

    public static class SexParser
    {
        public static Either<ParsingError, Sex> Parse(char potentialSex)
            => potentialSex switch
            {
                '1' => Sex.Sloubi,
                '2' => Sex.Gagna,
                '3' => Sex.Catact,
                _ => new ParsingError("Not a valid sex")
            };
    }
}