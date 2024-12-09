using FluentAssertions;
using Xunit;
using static GiftWish.Tests.ChildBuilder;
using static GiftWish.Tests.GiftRequestBuilder;

namespace GiftWish.Tests;

public class SantaServiceTests
{
    private readonly SantaService _service = new();

    [Fact]
    public void RequestIsApprovedForNiceChildWithFeasibleGift()
    {
        var niceChild = AChild()
            .WithBehavior(Behavior.Nice)
            .WithGiftRequest(
                AGiftRequest()
                    .WithFeasibility(true)
                    .Build())
            .Build();

        _service.EvaluateRequest(niceChild).Should().BeTrue();
    }

    [Fact]
    public void RequestIsDeniedForNaughtyChild()
    {
        var naughtyChild = AChild()
            .WithBehavior(Behavior.Naughty)
            .WithGiftRequest(
                AGiftRequest()
                    .WithFeasibility(true)
                    .Build())
            .Build();

        _service.EvaluateRequest(naughtyChild).Should().BeFalse();
    }

    [Fact]
    public void RequestIsDeniedForNiceChildWithInfeasibleGift()
    {
        var niceChildWithInfeasibleGift = AChild()
            .WithBehavior(Behavior.Nice)
            .WithGiftRequest(
                AGiftRequest()
                    .WithFeasibility(false)
                    .Build())
            .Build();

        _service.EvaluateRequest(niceChildWithInfeasibleGift).Should().BeFalse();
    }
}