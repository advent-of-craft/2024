namespace SantaMarket.Model
{
    public class ChristmasElf(ISantamarketCatalog catalog)
    {
        private readonly Dictionary<Product, Offer> _offers = new();

        public void AddSpecialOffer(SpecialOfferType offerType, Product product, double argument)
            => _offers[product] = new Offer(offerType, product, argument);

        public Receipt ChecksOutArticlesFrom(ShoppingSleigh thesleigh)
        {
            var receipt = new Receipt();
            var productQuantities = thesleigh.Items();

            foreach (var pq in productQuantities)
            {
                var p = pq.Product;
                var quantity = pq.Quantity;
                var unitPrice = catalog.GetUnitPrice(p);
                var price = quantity * unitPrice;
                receipt.AddProduct(p, quantity, unitPrice, price);
            }

            thesleigh.HandleOffers(receipt, _offers, catalog);

            return receipt;
        }
    }
}