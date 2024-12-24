using Delivery.Domain;
using Delivery.Domain.Core;
using Delivery.Tests.Assertions;
using Delivery.Tests.Doubles;
using Delivery.UseCases;
using FluentAssertions;
using FluentAssertions.LanguageExt;
using LanguageExt;
using Xunit;
using static Delivery.Domain.Core.Error;
using static Faker.Name;

namespace Delivery.Tests.UseCases
{
    public class ToyDelivery
    {
        private readonly InMemoryToyRepository _toyRepository;
        private readonly ToyDeliveryUseCase _useCase;

        protected ToyDelivery()
        {
            _toyRepository = new InMemoryToyRepository();
            _useCase = new ToyDeliveryUseCase(_toyRepository);
        }

        private Toy ForASuppliedToy(int stock = 1)
            => ToyBuilder.ToysInStock(stock)
                .Build()
                .Let(toy => _toyRepository.Save(toy));

        public class SuccessFully_When : ToyDelivery
        {
            [Fact]
            public void Toy_And_Update_Stock() =>
                ForASuppliedToy()
                    .Let(toy =>
                    {
                        var command = new DeliverToy(FullName(), toy.Name!);

                        _useCase.Handle(command)
                            .Should()
                            .Be(Unit.Default);

                        toy.Version.Should().Be(2);
                        toy.Should()
                            .HaveRaisedEvent(_toyRepository,
                                new StockReducedEvent(toy.Id, Time.Now,
                                    command.DesiredToy,
                                    StockUnit.From(0).RightUnsafe())
                            );
                    });
        }

        public class Fail_When : ToyDelivery
        {
            [Fact]
            public void Toy_Has_Not_Been_Built()
            {
                const string notBuiltToy = "Not a Bike";

                _useCase.Handle(new DeliverToy(FullName(), notBuiltToy))
                    .Should()
                    .Be(AnError("Oops we have a problem... we have not build the toy: Not a Bike"));

                AssertThatNoEventHasBeenRaised();
            }

            [Fact]
            public void Toy_Is_Not_Supplied_Anymore()
                => ForASuppliedToy(0)
                    .Let(toy =>
                    {
                        _useCase.Handle(new DeliverToy(FullName(), toy.Name!))
                            .Should()
                            .Be(AnError($"No more {toy.Name} in stock"));

                        AssertThatNoEventHasBeenRaised();
                    });

            private void AssertThatNoEventHasBeenRaised()
                => _toyRepository.RaisedEvents()
                    .Should()
                    .BeEmpty();
        }
    }
}