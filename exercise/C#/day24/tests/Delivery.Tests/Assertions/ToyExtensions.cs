using Delivery.Domain;

namespace Delivery.Tests.Assertions;

public static class ToyExtensions
{
    public static ToyAssertions Should(this Toy toy) => new(toy);
}