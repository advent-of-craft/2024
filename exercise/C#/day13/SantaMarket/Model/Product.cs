namespace SantaMarket.Model
{
    public class Product(string name, ProductUnit unit)
    {
        public string Name { get; } = name;
        public ProductUnit Unit { get; } = unit;

        public override bool Equals(object? obj) =>
            obj is Product product &&
            Name == product.Name &&
            Unit == product.Unit;

        public override int GetHashCode() => HashCode.Combine(Name, Unit);

        public override string ToString() => $"Product{{name='{Name}', unit={Unit}}}";
    }
}