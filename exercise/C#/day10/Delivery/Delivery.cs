namespace Delivery;

public static class Building
{
    public static int WhichFloor(string instructions)
        => CalculationParameters
            .CalculationParametersBuild(instructions)
            .CalculateBraceFloorIncrement()
            .Sum(kp => kp.Item2);

    private static List<Tuple<char, int>> CalculateBraceFloorIncrement(this CalculationParameters parameters)
        =>
        [
            new('(', parameters.OpenBrace * parameters.Instructions.Count(c => c == '(')),
            new(')', parameters.ClosingBrace * parameters.Instructions.Count(c => c == ')'))
        ];
}