using Delivery.Domain.Core;
using LanguageExt;
using static LanguageExt.Prelude;

namespace Delivery.Domain
{
    public class Toy : EventSourcedAggregate
    {
        public string? Name { get; private set; }
        private StockUnit _stock;

        private Toy(Func<DateTime> timeProvider, string name, StockUnit stock)
            : base(timeProvider) => RaiseEvent(new ToyCreatedEvent(Guid.NewGuid(), timeProvider(), name, stock));

        public static Either<Error, Toy> Create(Func<DateTime> timeProvider, string name, int stock)
            => StockUnit
                .From(stock)
                .Map(s => new Toy(timeProvider, name, s));

        private void Apply(ToyCreatedEvent @event)
        {
            Id = @event.Id;
            Name = @event.Name;
            _stock = @event.Stock;
        }

        public Either<Error, Toy> ReduceStock()
        {
            if (!_stock.IsSupplied()) return Left(new Error($"No more {Name} in stock"));
            RaiseEvent(new StockReducedEvent(Id, Time(), Name!, _stock.Decrease()));
            return this;
        }

        private void Apply(StockReducedEvent @event) => _stock = @event.NewStock;

        protected override void RegisterRoutes()
        {
            RegisterEventRoute<ToyCreatedEvent>(Apply);
            RegisterEventRoute<StockReducedEvent>(Apply);
        }
    }
}