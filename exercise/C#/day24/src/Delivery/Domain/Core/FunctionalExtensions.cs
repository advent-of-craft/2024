namespace Delivery.Domain.Core
{
    public static class FunctionalExtensions
    {
        public static T Let<T>(this T obj, Action<T> action)
        {
            if (obj != null) action(obj);
            return obj;
        }
    }
}