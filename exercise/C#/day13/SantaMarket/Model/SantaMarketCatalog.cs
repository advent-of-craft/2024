namespace SantaMarket.Model
{
    public interface ISantamarketCatalog
    {
        void AddProduct(Product product, double price);
        double GetUnitPrice(Product product);
    }
}