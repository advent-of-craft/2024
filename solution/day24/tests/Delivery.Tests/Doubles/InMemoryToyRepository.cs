using LanguageExt;
using static LanguageExt.Option<D.A>;

namespace D.Tests.Doubles
{
    public class InMemoryItr : ITR
    {
        private Map<Guid, A> _toys;
        private Seq<IJ> _raisedEvents;

        public Option<A> F(string t)
            => _toys.Filter(toy => toy.Yes == t)
                .Values
                .ToOption();

        public Option<A> jF(Guid u)
            => _toys.ContainsKey(u)
                ? _toys[u]
                : None;

        public void Delete(A v)
        {
            _raisedEvents = [];
            _toys = _toys.AddOrUpdate(v.h, v);

            ((IInterface) v).Set()
                .ToList()
                .ForEach(@event => { _raisedEvents = _raisedEvents.Add(@event); });

            ((IInterface) v).DEL();
        }

        public Seq<IJ> RaisedEvents() => _raisedEvents;
    }
}