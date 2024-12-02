using System.Collections.ObjectModel;

namespace Games;

public class Configuration
{
    private readonly List<FizzBuzzParam> _mapping;
    private readonly Range _range;
    private Configuration(List<FizzBuzzParam> mapping, Range range)
    {
        _mapping = mapping;
        _range = range;
    }
    public static Configuration Create(Dictionary<int, string> mapping, Range range)
    {
        EnsureKeyArePrimeNumbers(mapping);
        
        return new Configuration(mapping
            .Select(m => new FizzBuzzParam(m.Key, m.Value))
            .OrderBy(x => x.Divisor)
            .ToList()
            , range);
    }
    private static void EnsureKeyArePrimeNumbers(Dictionary<int, string> mapping)
    {
        if (mapping.Keys.Any(key => !IsPrime(key)))
        {
            throw new ArgumentException("All keys in the mapping must be prime numbers.");
        }
    }
    
    /// <summary>
    /// Validate Prime number for not too long numbers (not optimized)
    /// </summary>
    /// <param name="number"></param>
    /// <returns></returns>
    private static bool IsPrime(int number) 
        => number > 1 && Enumerable.Range(2, (int)Math.Sqrt(number) - 1)
            .All(divisor => number % divisor != 0);

    public ReadOnlyCollection<FizzBuzzParam> Mapping => new(_mapping);
    public bool IsOutOfRange(int input) => input < _range.Min || input > _range.Max;
    public int Min => _range.Min;
    public int Max => _range.Max;
}

public record FizzBuzzParam(int Divisor, string Value);