namespace SantaMarket.Model
{
    public class Offer(SpecialOfferType offerType, Product product, double argument)
    {
        public SpecialOfferType OfferType { get; } = offerType;
        public double Argument { get; } = argument;
    }
}