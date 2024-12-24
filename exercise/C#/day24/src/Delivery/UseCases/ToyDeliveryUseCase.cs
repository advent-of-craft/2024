using Delivery.Domain;
using Delivery.Domain.Core;
using LanguageExt;
using static Delivery.Domain.Core.Error;

namespace Delivery.UseCases
{
    public class ToyDeliveryUseCase(IToyRepository repository)
    {
        public Either<Error, Unit> Handle(DeliverToy deliverToy)
            => repository
                .FindByName(deliverToy.DesiredToy)
                .ToEither(() => ErrorFor(deliverToy))
                .Bind(ReduceStock)
                .Map(_ => Unit.Default);

        private Either<Error, Toy> ReduceStock(Toy toy)
            => toy.ReduceStock()
                .Let(_ => repository.Save(toy));

        private static Error ErrorFor(DeliverToy deliverToy)
            => AnError($"Oops we have a problem... we have not build the toy: {deliverToy.DesiredToy}");
    }
}