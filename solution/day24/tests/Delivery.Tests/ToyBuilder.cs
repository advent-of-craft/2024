namespace D.Tests;

public class ToyBuilder
{
    private readonly int _stock;

    private ToyBuilder(int stock) => _stock = stock;

    public static ToyBuilder ToysInStock(int stock = 1) => new(stock);

    public A Build() => A.Create(Time.Provider, Faker.Lorem.GetFirstWord(), _stock).RightUnsafe();
}