using FluentAssertions;
using Xunit;

namespace GiftWish.Tests
{
    public class SantaServiceTests
    {
        private readonly SantaService _service = new();

        [Fact]
        public void RequestIsApprovedForNiceChildWithFeasibleGift()
        {
            var niceChild = new Child("Alice", "Thomas", 9, Behavior.Nice, new GiftRequest("Bicycle", true, Priority.NiceToHave));
            _service.EvaluateRequest(niceChild).Should().BeTrue();
        }

        [Fact]
        public void RequestIsDeniedForNaughtyChild()
        {
            var naughtyChild = new Child("Noa", "Thierry", 6, Behavior.Naughty, new GiftRequest("SomeToy", true, Priority.Dream));
            _service.EvaluateRequest(naughtyChild).Should().BeFalse();
        }

        [Fact]
        public void RequestIsDeniedForNiceChildWithInfeasibleGift()
        {
            var niceChildWithInfeasibleGift = new Child("Charlie", "Joie", 3, Behavior.Nice, new GiftRequest("AnotherToy", false, Priority.Dream));
            _service.EvaluateRequest(niceChildWithInfeasibleGift).Should().BeFalse();
        }
    }
}
