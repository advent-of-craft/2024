namespace SantaMarket.Model
{
    public class ReceiptItem
    {
        public ReceiptItem(Product product, double quantity, double price, double totalPrice)
        {
            Product = product;
            Quantity = quantity;
            Price = price;
            TotalPrice = totalPrice;
        }

        public double Price { get; }

        public Product Product { get; }

        public double Quantity { get; }

        public double TotalPrice { get; }

        public override bool Equals(object? obj)
        {
            if (ReferenceEquals(this, obj)) return true;
            if (obj is null || GetType() != obj.GetType()) return false;

            var that = (ReceiptItem) obj;
            const double tolerance = 0.001;

            return Math.Abs(that.Price - Price) < tolerance &&
                   Math.Abs(that.TotalPrice - TotalPrice) < tolerance &&
                   Math.Abs(that.Quantity - Quantity) < tolerance &&
                   Equals(Product, that.Product);
        }

        public override int GetHashCode() => HashCode.Combine(Product, Price, TotalPrice, Quantity);

        public override string ToString() =>
            $"ReceiptItem{{product={Product}, price={Price}, totalPrice={TotalPrice}, quantity={Quantity}}}";
    }
}