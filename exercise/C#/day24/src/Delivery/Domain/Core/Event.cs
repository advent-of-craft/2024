namespace Delivery.Domain.Core
{
    public record Event(Guid Id, int Version, DateTime Date) : IEvent;
}