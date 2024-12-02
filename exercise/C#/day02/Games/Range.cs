namespace Games;

public class Range
{
    private Range(int min, int max)
    {
        Min = min;
        Max = max;
    }
    public int Min { get; }
    public int Max { get; }

    public static Range Create(int min, int max)
    {
        if(min > max)
            throw new ArgumentException("The maximum value must be greater than the minimum value.");
        
        return new Range(min, max);
    }
}