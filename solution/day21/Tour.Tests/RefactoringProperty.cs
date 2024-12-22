using FsCheck;
using FsCheck.Xunit;
using LanguageExt;

namespace Tour.Tests
{
    public class RefactoringProperty
    {
        private static readonly Arbitrary<Step> StepGenerator =
        (
            from hour in Gen.Choose(0, 23)
            from minute in Gen.Choose(0, 59)
            from second in Gen.Choose(0, 59)
            from label in Arb.Default.StringWithoutNullChars().Generator
            from duration in Gen.Choose(1, 60)
            select new Step(new TimeOnly(hour, minute, second), label.Item, duration)
        ).ToArbitrary();

        private static readonly Arbitrary<Seq<Step>> StepsGenerator =
            Gen.Choose(0, 100)
                .Select(x => StepGenerator.Generator.Sample(x, x)
                    .ToSeq())
                .ToArbitrary();

        [Property]
        public Property NewImplementationShouldReturnTheSameResult()
            => Prop.ForAll(StepsGenerator,
                steps => new TourCalculator(steps.ToList()).Calculate()
                         == ToV1Result(steps));

        private static Either<string, string> ToV1Result(Seq<Step> steps)
            => StepsTextFormatter.Format(steps).MapLeft(err => err.Message);
    }
}