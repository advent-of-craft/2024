using LanguageExt;

namespace Games
{
    public static class FizzBuzz
    {
        public const int Min = 1;
        public const int Max = 100;

        private static readonly Dictionary<int, string> Mapping = new()
            {
                {15, "FizzBuzz"},
                {3, "Fizz"},
                {5, "Buzz"}
            };

        public static Option<string> Convert(int input)
            => IsOutOfRange(input)
                ? Option<string>.None
                : ConvertSafely(input);

        private static string ConvertSafely(int input)
            => Mapping
                .Find(p => Is(p.Key, input))
                .Map(kvp => kvp.Value)
                .FirstOrDefault(input.ToString());

        private static bool Is(int divisor, int input) => input % divisor == 0;

        private static bool IsOutOfRange(int input) => input is < Min or > Max;
    }
}