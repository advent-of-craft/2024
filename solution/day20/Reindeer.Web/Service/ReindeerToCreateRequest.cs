namespace Reindeer.Web.Service
{
    public record ReindeerToCreateRequest(string Name, ReindeerColor Color)
    {
        public ReindeerToCreate ToDomain() => new(Guid.NewGuid(), Name, Color);
    }
}