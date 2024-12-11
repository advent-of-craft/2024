namespace Christmas
{
    public static class Extensions
    {
        public static TOut Do<TIn, TOut>(this TIn it, Func<TIn, TOut> func) => func(it);
    }
}