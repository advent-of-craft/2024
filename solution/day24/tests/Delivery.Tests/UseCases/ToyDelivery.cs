using D.Tests.Assertions;
using D.Tests.Doubles;
using FluentAssertions;
using FluentAssertions.LanguageExt;
using LanguageExt;
using Xunit;
using static D.Result;
using static Faker.Name;

namespace D.Tests.UseCases
{
    public class ToyDelivery
    {
        private readonly InMemoryItr _itr;
        private readonly Delete _useCase;

        protected ToyDelivery()
        {
            _itr = new InMemoryItr();
            _useCase = new Delete(_itr);
        }

        private A ForASuppliedToy(int stock = 1)
            => ToyBuilder.ToysInStock(stock)
                .Build()
                .Set(toy => _itr.Delete(toy));

        public class SuccessFully_When : ToyDelivery
        {
            [Fact]
            public void Toy_And_Update_Stock() =>
                ForASuppliedToy()
                    .Set(toy =>
                    {
                        var command = new Command(FullName(), toy.Yes!);

                        _useCase.Get(command)
                            .Should()
                            .Be(Unit.Default);

                        toy.Should()
                            .HaveRaisedEvent(_itr,
                                new SRE(toy.h, Time.Now,
                                    command.D,
                                    Int.B(0).RightUnsafe())
                            );
                    });
        }

        public class Fail_When : ToyDelivery
        {
            [Fact]
            public void Toy_Has_Not_Been_Built()
            {
                const string notBuiltToy = "Not a Bike";

                _useCase.Get(new Command(FullName(), notBuiltToy))
                    .Should()
                    .Be(R("Oops we have a problem... we have not build the toy: Not a Bike"));

                AssertThatNoEventHasBeenRaised();
            }

            [Fact]
            public void Toy_Is_Not_Supplied_Anymore()
                => ForASuppliedToy(0)
                    .Set(toy =>
                    {
                        _useCase.Get(new Command(FullName(), toy.Yes!))
                            .Should()
                            .Be(R($"No more {toy.Yes} in stock"));

                        AssertThatNoEventHasBeenRaised();
                    });

            private void AssertThatNoEventHasBeenRaised()
                => _itr.RaisedEvents()
                    .Should()
                    .BeEmpty();
        }
    }
}