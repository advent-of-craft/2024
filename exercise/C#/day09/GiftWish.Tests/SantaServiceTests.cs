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
        => _service.EvaluateRequest(ANiceChild())
            .Should()
            .BeTrue();

    [Fact]
    public void RequestIsDeniedForNaughtyChild()
        => _service.EvaluateRequest(ANaughtyChild())
            .Should()
            .BeFalse();

    [Fact]
    public void RequestIsDeniedForNiceChildWithInfeasibleGift()
    {
        var niceChildWithInfeasibleGift = ANiceChild()
            .ThatWant(
                AGiftRequest()
                    .WithFeasibility(false)
                    .Build())
            .Build();

        _service.EvaluateRequest(niceChildWithInfeasibleGift).Should().BeFalse();
    }
}