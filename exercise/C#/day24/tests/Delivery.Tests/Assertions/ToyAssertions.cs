using Delivery.Domain;
using Delivery.Domain.Core;
using Delivery.Tests.Doubles;
using FluentAssertions.Execution;
using FluentAssertions.Primitives;

namespace Delivery.Tests.Assertions
{
    public class ToyAssertions(Toy toy) : ReferenceTypeAssertions<Toy?, ToyAssertions>(toy)
    {
        protected override string Identifier => "toy";

        public void HaveRaisedEvent<TEvent>(InMemoryToyRepository repository, TEvent expectedEvent)
            where TEvent : class, IEvent
            => Execute.Assertion
                .ForCondition(repository.RaisedEvents().Last.Equals(expectedEvent))
                .FailWith($"Raised events should contain {expectedEvent}.");
    }
}