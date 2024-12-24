using Delivery.Domain;
using Delivery.Domain.Core;
using LanguageExt;
using static LanguageExt.Option<Delivery.Domain.Toy>;

namespace Delivery.Tests.Doubles
{
    public class InMemoryToyRepository : IToyRepository
    {
        private Map<Guid, Toy> _toys;
        private Seq<IEvent> _raisedEvents;

        public Option<Toy> FindByName(string toyName)
            => _toys.Filter(toy => toy.Name == toyName)
                .Values
                .ToOption();

        public Option<Toy> FindById(Guid id)
            => _toys.ContainsKey(id)
                ? _toys[id]
                : None;

        public void Save(Toy toy)
        {
            _raisedEvents = [];
            _toys = _toys.AddOrUpdate(toy.Id, toy);

            ((IAggregate) toy).GetUncommittedEvents()
                .ToList()
                .ForEach(@event => { _raisedEvents = _raisedEvents.Add(@event); });

            ((IAggregate) toy).ClearUncommittedEvents();
        }

        public Seq<IEvent> RaisedEvents() => _raisedEvents;
    }
}