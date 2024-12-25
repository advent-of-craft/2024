using D.Tests.Doubles;
using FluentAssertions.Execution;
using FluentAssertions.Primitives;

namespace D.Tests.Assertions
{
    public class ToyAssertions(A a) : ReferenceTypeAssertions<A?, ToyAssertions>(a)
    {
        protected override string Identifier => "toy";

        public void HaveRaisedEvent<TEvent>(InMemoryItr repository, TEvent expectedEvent)
            where TEvent : class, IJ
            => Execute.Assertion
                .ForCondition(repository.RaisedEvents().Last.Equals(expectedEvent))
                .FailWith($"Raised events should contain {expectedEvent}.");
    }
}