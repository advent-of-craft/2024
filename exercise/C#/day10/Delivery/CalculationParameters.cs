namespace Delivery;

public class CalculationParameters(string instructions, int openBrace, int closingBrace)
{
    private const string ElfEmoji = "🧝";
    public int OpenBrace { get; } = openBrace;
    public int ClosingBrace { get; } = closingBrace;
    public int OpenBraceCount => instructions.Count(c => c == '(');
    public int ClosingBraceCount => instructions.Count(c => c == ')');

    public static CalculationParameters Create(string instructions)
    {
        return instructions.Contains(ElfEmoji) 
            ? new(instructions.Replace(ElfEmoji, ""), -2,3) 
            : new (instructions, 1,-1);
    }
}