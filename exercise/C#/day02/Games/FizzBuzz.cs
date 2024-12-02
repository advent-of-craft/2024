using LanguageExt;

namespace Games;

public class FizzBuzz(Configuration configuration)
{
    public Option<string> Convert(int input)
        => configuration.IsOutOfRange(input)
            ? Option<string>.None
            : ConvertSafely(input);

    private string ConvertSafely(int input)
        => string.Join("", configuration.Mapping
            .Where(p => Is(p.Divisor, input))
            .Map(p => p.Value)
            .DefaultIfEmpty(input.ToString()));

    private static bool Is(int divisor, int input) => input % divisor == 0;
}