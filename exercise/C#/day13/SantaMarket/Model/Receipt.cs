using System.Collections.ObjectModel;

namespace SantaMarket.Model
{
    public class Receipt
    {
        private readonly List<ReceiptItem> _items = [];
        private readonly List<Discount> _discounts = [];

        public double TotalPrice()
        {
            var total = _items.Sum(item => item.TotalPrice);
            total += _discounts.Sum(discount => discount.DiscountAmount);
            return total;
        }

        public void AddProduct(Product product, double quantity, double price, double totalPrice) =>
            _items.Add(new ReceiptItem(product, quantity, price, totalPrice));

        public IReadOnlyList<ReceiptItem> Items() => new ReadOnlyCollection<ReceiptItem>(_items);

        public void AddDiscount(Discount discount) => _discounts.Add(discount);

        public IReadOnlyList<Discount> GetDiscounts() => new ReadOnlyCollection<Discount>(_discounts);

        public override bool Equals(object? obj)
        {
            if (ReferenceEquals(this, obj)) return true;
            if (obj is null || GetType() != obj.GetType()) return false;

            var receipt = (Receipt) obj;
            return _items.SequenceEqual(receipt._items) && _discounts.SequenceEqual(receipt._discounts);
        }

        public override int GetHashCode() => HashCode.Combine(_items, _discounts);
    }
}