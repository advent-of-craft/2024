namespace Delivery.Domain.Core
{
    public interface IEvent
    {
        Guid Id { get; }
        int Version { get; }
        DateTime Date { get; }
    }
}