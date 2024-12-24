using LanguageExt;

namespace Delivery.Tests
{
    public static class LangExtExtensions
    {
        public static TRight RightUnsafe<TLeft, TRight>(this Either<TLeft, TRight> either) => either.RightToSeq()[0];

        public static TLeft LeftUnsafe<TLeft, TRight>(this Either<TLeft, TRight> either) => either.LeftToSeq()[0];
    }
}