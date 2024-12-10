namespace Delivery;

public class CalculationParameters(string instructions, int openBrace, int closingBrace)
{
    private const string ElfEmoji = "🧝";
    public string Instructions { get; } = instructions;
    public int OpenBrace { get; } = openBrace;
    public int ClosingBrace { get; } = closingBrace;

    public static CalculationParameters CalculationParametersBuild(string instructions)
    {
        return instructions.Contains(ElfEmoji) 
            ? new(instructions.Replace(ElfEmoji, ""), -2,3) 
            : new (instructions, 1,-1);
    }
}