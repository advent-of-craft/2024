using LanguageExt;

namespace Delivery.Domain.Core
{
    internal class RegisteredRoutes
    {
        private Map<string, Action<IEvent>> _routes = new();

        public void Dispatch<TEvent>(TEvent @event) where TEvent : IEvent
            => _routes[@event.GetType().ToString()](@event);

        public void Register(string eventType, Action<IEvent> apply)
            => _routes = _routes.Add(eventType, apply);
    }
}