using LanguageExt;
using static System.Environment;
using static System.TimeSpan;

namespace Tour
{
    public static class StepsTextFormatter
    {
        public static Either<SantaError, string> Format(Seq<Step> steps)
            => steps.IsEmpty
                ? new SantaError("No locations !!!")
                : StatementFor(steps)
                  + FormatTotal(steps)
                  + NewLine;

        private static string StatementFor(Seq<Step> steps)
            => steps.OrderBy(s => s.Time)
                .Fold(string.Empty, (statement, step) => statement + FormatLine(step) + NewLine);

        private static string FormatLine(Step step)
            => $"{step.Time} : {step.Label} | {step.DeliveryTime} sec";

        private static string FormatTotal(Seq<Step> steps)
            => $"Delivery time | {FormatDeliveryTime(steps)}";

        private static string FormatDeliveryTime(Seq<Step> steps)
            => FromSeconds(steps.Sum(step => step.DeliveryTime))
                .ToString(@"hh\:mm\:ss");
    }
}