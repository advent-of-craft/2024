using ToyProduction.Domain;

namespace ToyProduction.Tests.Doubles
{
    public class InMemoryToyRepository : IToyRepository
    {
        private readonly List<Toy> _toys = [];

        public Toy? FindByName(string name) => _toys.FirstOrDefault(t => t.Name == name);

        public void Save(Toy toy)
        {
            _toys.Remove(toy);
            _toys.Add(toy);
        }
    }
}