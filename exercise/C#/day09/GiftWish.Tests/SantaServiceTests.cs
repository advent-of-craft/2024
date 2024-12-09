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
        var niceChild = ANiceChild()
            .Build();

        _service.EvaluateRequest(niceChild).Should().BeTrue();
    }

    [Fact]
    public void RequestIsDeniedForNaughtyChild()
    {
        var naughtyChild = ANaughtyChild()
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
        var niceChildWithInfeasibleGift = ANiceChild()
            .WithGiftRequest(
                AGiftRequest()
                    .WithFeasibility(false)
                    .Build())
            .Build();

        _service.EvaluateRequest(niceChildWithInfeasibleGift).Should().BeFalse();
    }
}