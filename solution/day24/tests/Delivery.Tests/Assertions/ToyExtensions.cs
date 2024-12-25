namespace D.Tests.Assertions;

public static class ToyExtensions
{
    public static ToyAssertions Should(this A a) => new(a);
}