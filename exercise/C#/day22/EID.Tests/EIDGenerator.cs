using System.Diagnostics.CodeAnalysis;
using FsCheck;

namespace EID.Tests
{
    public static class EIDGenerator
    {
        [SuppressMessage("FSCheck", "UnusedMember.Local", Justification = "Used by FSCheck")]
        public static Arbitrary<EID> ValidEid()
            => (from sex in Arb.Generate<Sex>()
                    from year in Gen.Choose(0, 99)
                    from serialNumber in Gen.Choose(1, 999)
                    select new EID(sex, (Year) year, (SerialNumber) serialNumber)
                ).ToArbitrary();
    }
}