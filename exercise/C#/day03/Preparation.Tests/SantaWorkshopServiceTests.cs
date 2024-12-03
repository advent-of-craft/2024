using FluentAssertions;
using Xunit;

namespace Preparation.Tests
{
    public class SantaWorkshopServiceTests
    {
        private const string RecommendedAge = "recommendedAge";
        private readonly SantaWorkshopService _service = new();

        [Fact]
        public void PrepareGift_WithValidToy_ShouldInstantiateIt()
        {
            const string giftName = "Bitzee";
            const double weight = 3;
            const string color = "Purple";
            const string material = "Plastic";

            _service.PrepareGift(giftName, weight, color, material)
                .Should()
                .NotBeNull();
        }

        [Fact]
        public void RetrieveAttributeOnGift()
        {
            const string giftName = "Furby";
            const double weight = 1;
            const string color = "Multi";
            const string material = "Cotton";

            var gift = _service.PrepareGift(giftName, weight, color, material);
            gift.AddAttribute(RecommendedAge, "3");

            gift.RecommendedAge()
                .Should()
                .Be(3);
        }

        [Fact]
        public void FailsForATooHeavyGift()
        {
            const string giftName = "Dog-E";
            const double weight = 6;
            const string color = "White";
            const string material = "Metal";

            var prepareGift = () => _service.PrepareGift(giftName, weight, color, material);

            prepareGift.Should()
                .Throw<ArgumentException>()
                .WithMessage("Gift is too heavy for Santa's sleigh");
        }
    }
}