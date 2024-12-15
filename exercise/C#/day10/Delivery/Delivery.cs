namespace Delivery;

public static class Building
{
    public static int WhichFloor(string instructions)
        => CalculationParameters
            .Create(instructions)
            .CalculateBraceFloorIncrement();

    private static int CalculateBraceFloorIncrement(this CalculationParameters parameters)
        => parameters.OpenBrace * parameters.OpenBraceCount + parameters.ClosingBrace * parameters.ClosingBraceCount;
}