using Delivery.Domain;

namespace Delivery.Tests;

public class ToyBuilder
{
    private readonly int _stock;

    private ToyBuilder(int stock) => _stock = stock;

    public static ToyBuilder ToysInStock(int stock = 1) => new(stock);

    public Toy Build() => Toy.Create(Time.Provider, Faker.Lorem.GetFirstWord(), _stock).RightUnsafe();
}