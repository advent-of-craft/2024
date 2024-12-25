namespace D.Tests;

public static class Time
{
    public static readonly DateTime Now = new(2024, 12, 24, 23, 30, 45, kind: DateTimeKind.Utc);
    public static readonly Func<DateTime> Provider = () => Now;
}