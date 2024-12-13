namespace SantaMarket.Model
{
    public class ProductQuantity(Product product, double quantity)
    {
        public Product Product { get; } = product;
        public double Quantity { get; } = quantity;

        public override string ToString() => $"ProductQuantity{{product={Product}, quantity={Quantity}}}";
    }
}