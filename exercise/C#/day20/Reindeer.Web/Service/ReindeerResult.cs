namespace Reindeer.Web.Service
{
    public class ReindeerResult
    {
        public Guid Id { get; }
        public string Name { get; }
        public ReindeerColor Color { get; }

        private ReindeerResult(Guid id, string name, ReindeerColor color)
        {
            Id = id;
            Name = name;
            Color = color;
        }

        public static ReindeerResult From(Reindeer reindeer) => new(reindeer.Id, reindeer.Name, reindeer.Color);
    }
}