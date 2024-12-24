using LanguageExt;

namespace Delivery.Domain.Core
{
    public interface IRepository<TAggregate>
        where TAggregate : EventSourcedAggregate
    {
        Option<TAggregate> FindById(Guid id);
        void Save(TAggregate aggregate);
    }
}