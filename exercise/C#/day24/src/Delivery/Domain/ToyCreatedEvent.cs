using Delivery.Domain.Core;

namespace Delivery.Domain
{
    internal record ToyCreatedEvent(Guid Id, DateTime Date, string Name, StockUnit Stock) : Event(Id, 1, Date);
}