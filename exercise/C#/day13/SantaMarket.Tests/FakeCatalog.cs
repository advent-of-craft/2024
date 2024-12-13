using SantaMarket.Model;

namespace SantaMarket.Tests
{
    public class FakeCatalog : ISantamarketCatalog
    {
        private readonly Dictionary<string, Product> _products = new();
        private readonly Dictionary<string, double> _prices = new();

        public void AddProduct(Product product, double price)
        {
            _products[product.Name] = product;
            _prices[product.Name] = price;
        }

        public double GetUnitPrice(Product product) => _prices[product.Name];
    }
}