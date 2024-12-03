namespace Preparation
{
    public class Gift(string name, double weight, string color, string material)
    {
        private readonly Dictionary<string, string> _attributes = new();
        public void AddAttribute(string key, string value) => _attributes[key] = value;
        public int RecommendedAge() => int.TryParse(_attributes["recommendedAge"], out var age) ? age : 0;
        public override string ToString() => $"A {color}-colored {name} weighting {weight} kg made in {material}";
    }
}