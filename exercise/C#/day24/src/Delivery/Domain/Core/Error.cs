namespace Delivery.Domain.Core
{
    public record Error(string Message)
    {
        public static Error AnError(string message) => new(message);
    }
}