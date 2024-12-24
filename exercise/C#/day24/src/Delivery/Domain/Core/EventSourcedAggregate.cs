using LanguageExt;

namespace Delivery.Domain.Core
{
    public abstract class EventSourcedAggregate : IAggregate, IEquatable<IAggregate>, IEqualityComparer<IAggregate>
    {
        private readonly ICollection<IEvent> _uncommittedEvents = new LinkedList<IEvent>();
        private readonly RegisteredRoutes _registeredRoutes = new();
        private readonly Func<DateTime> _timeProvider;

        protected EventSourcedAggregate(Func<DateTime> timeProvider)
        {
            _timeProvider = timeProvider;
            RegisterRoutes();
        }

        public Guid Id { get; protected set; }

        public int Version { get; private set; }

        protected abstract void RegisterRoutes();

        void IAggregate.ApplyEvent(IEvent @event)
        {
            _registeredRoutes.Dispatch(@event);
            Version++;
        }

        protected void RegisterEventRoute<TEvent>(Action<TEvent> apply) where TEvent : class, IEvent
            => _registeredRoutes.Register(typeof(TEvent).ToString(), @event => apply((@event as TEvent)!));

        Seq<IEvent> IAggregate.GetUncommittedEvents() => _uncommittedEvents.ToSeq();

        void IAggregate.ClearUncommittedEvents() => _uncommittedEvents.Clear();

        bool IEquatable<IAggregate>.Equals(IAggregate? other) => Equals(other);

        protected void RaiseEvent(IEvent @event)
        {
            ((IAggregate) this).ApplyEvent(@event);
            _uncommittedEvents.Add(@event);
        }

        public override int GetHashCode() => Id.GetHashCode();

        private bool Equals(IAggregate? other) => null != other && other.Id == Id;

        public override bool Equals(object? obj) => Equals(obj as IAggregate);

        protected DateTime Time() => _timeProvider();

        public bool Equals(IAggregate? x, IAggregate? y)
            => x != null && y != null && (ReferenceEquals(x, y) || x.Id.Equals(y.Id));

        public int GetHashCode(IAggregate obj) => GetHashCode();
    }
}