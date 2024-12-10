namespace Delivery;

public static class Building
{
    private const string ElfEmoji = "🧝";
    
    public static int WhichFloor(string instructions)
    {
        var parameters = instructions.BuildParameters();

        return instructions.Replace(ElfEmoji, "")
             .CalculateBraceFloorIncrement(parameters)
             .Sum(kp => kp.Item2);
    }
        
    private static List<Tuple<char, int>> CalculateBraceFloorIncrement(this string instructions, CalculationParameters braceWeights) 
        => [
            new('(', braceWeights.OpenBrace * instructions.Count(c => c == '(')),
            new(')', braceWeights.ClosingBrace * instructions.Count(c => c == ')'))
        ];

    private static CalculationParameters BuildParameters(this string instructions) 
        => instructions.Contains(ElfEmoji) 
            ? new(-2,3) 
            : new (1,-1);
}