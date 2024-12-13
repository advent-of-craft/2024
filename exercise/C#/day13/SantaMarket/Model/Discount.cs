namespace SantaMarket.Model
{
    public class Discount(Product product, string description, double discountAmount)
    {
        public string Description { get; } = description;
        public double DiscountAmount { get; } = discountAmount;
        public Product Product { get; } = product;

        public override bool Equals(object? obj)
        {
            if (ReferenceEquals(this, obj)) return true;
            if (obj is null || GetType() != obj.GetType()) return false;

            var discount = (Discount) obj;
            const double tolerance = 0.001;

            return Math.Abs(discount.DiscountAmount - DiscountAmount) < tolerance &&
                   Description == discount.Description &&
                   Equals(Product, discount.Product);
        }

        public override int GetHashCode() => HashCode.Combine(Description, DiscountAmount, Product);

        public override string ToString() =>
            $"Discount{{description='{Description}', discountAmount={DiscountAmount}, product={Product}}}";
    }
}