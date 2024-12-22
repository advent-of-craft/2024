using System.Diagnostics.CodeAnalysis;
using FsCheck;

namespace EID.Tests
{
    public class EIDProperties
    {
        [FsCheck.Xunit.Property(Arbitrary = [typeof(EIDGenerator)])]
        public Property RoundTripEID(EID eid) =>
            EID.Parse(eid.ToString())
                .Exists(parsedEID => parsedEID == eid)
                .ToProperty();

        public record Mutator(string Name, Func<EID, Gen<string>> Mutate)
        {
            public string Apply(EID eid) => Mutate(eid).Sample(0, 1).Head;
        }

        private static class MutatorGenerator
        {
            private static readonly Mutator AMutator = new("A mutator",
                eid => Gen.Elements("Implement this first mutator")
            );

            [SuppressMessage("FSCheck", "UnusedMember.Local", Justification = "Used by FSCheck")]
            public static Arbitrary<Mutator> Mutator() =>
                Gen.Elements(AMutator)
                    .ToArbitrary();
        }

        [FsCheck.Xunit.Property(Arbitrary = [typeof(EIDGenerator), typeof(MutatorGenerator)])]
        public Property InvalidEIDCanNeverBeParsed(EID eid, Mutator mutator) =>
            EID.Parse(mutator.Apply(eid))
                .IsLeft
                .ToProperty()
                .Classify(true, mutator.Name);
    }
}