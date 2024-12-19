namespace Travel
{
public static class SantaTravelCalculator
{
    public static int CalculateTotalDistanceRecursively(int numberOfReindeers)
    {
        if (numberOfReindeers == 1) return 1;
        checked
        {
            return 2 * CalculateTotalDistanceRecursively(numberOfReindeers - 1) + 1;
        }
    }
}
}