using FsCheck;
using FsCheck.Xunit;
using static System.Int32;

namespace D.Tests.Units
{
    public class CreateToyTests
    {
        private static readonly Arbitrary<int> InvalidStock = Gen.Choose(MinValue, -1).ToArbitrary();
        private static readonly Arbitrary<int> ValidStock = Gen.Choose(0, MaxValue).ToArbitrary();

        [Property]
        public Property Can_Not_Create_Toy_With_Invalid_Stock()
            => Prop.ForAll(InvalidStock, stock => A.Create(Time.Provider, "", stock).IsLeft);

        [Property]
        public Property Can_Create_Toy_With_Valid_Stock()
            => Prop.ForAll(ValidStock, stock => A.Create(Time.Provider, "", stock));
    }
}