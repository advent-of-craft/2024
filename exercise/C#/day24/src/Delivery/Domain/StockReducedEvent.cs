using Delivery.Domain.Core;

namespace Delivery.Domain
{
    public record StockReducedEvent(Guid Id, DateTime Date, string ToyName, StockUnit NewStock) : Event(Id, 1, Date);
}