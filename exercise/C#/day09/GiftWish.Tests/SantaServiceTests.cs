using FluentAssertions;
using Xunit;
using static GiftWish.Tests.GiftRequestBuilder;

namespace GiftWish.Tests;

public class SantaServiceTests
{
    private readonly SantaService _service = new();

    [Fact]
    public void RequestIsApprovedForNiceChildWithFeasibleGift()
    {
        var giftRequest = AGiftRequest()
            .WithFeasibility(true)
            .Build();
        
        var niceChild = new Child("Alice", "Thomas", 9, Behavior.Nice, giftRequest);
        _service.EvaluateRequest(niceChild).Should().BeTrue();
    }

    [Fact]
    public void RequestIsDeniedForNaughtyChild()
    {
        var giftRequest = AGiftRequest()
            .WithFeasibility(true)
            .Build();
        
        var naughtyChild = new Child("Noa", "Thierry", 6, Behavior.Naughty, giftRequest);
        _service.EvaluateRequest(naughtyChild).Should().BeFalse();
    }

    [Fact]
    public void RequestIsDeniedForNiceChildWithInfeasibleGift()
    {
        var giftRequest = AGiftRequest()
            .WithFeasibility(false)
            .Build();
        
        var niceChildWithInfeasibleGift = new Child("Charlie", "Joie", 3, Behavior.Nice, giftRequest);
        _service.EvaluateRequest(niceChildWithInfeasibleGift).Should().BeFalse();
    }
}