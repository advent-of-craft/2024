namespace Reindeer.Web.Service
{
    public sealed record Reindeer(Guid Id, string Name, ReindeerColor Color);

    public enum ReindeerColor
    {
        White = 0,
        Black = 1,
        Purple = 2 // Why not?
    }
}