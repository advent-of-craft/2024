using FluentAssertions;
using Xunit;
using static GiftWish.Tests.Builders.ChildBuilder;
using static GiftWish.Tests.Builders.GiftRequestBuilder;

namespace GiftWish.Tests;

public class SantaServiceTests
{
    private readonly SantaService _service = new();

    [Fact]
    public void RequestIsApprovedForNiceChildWithFeasibleGift()
        => _service.EvaluateRequest(ANiceChild().ThatWant(AFeasibleGift()))
            .Should()
            .BeTrue();

    [Fact]
    public void RequestIsDeniedForNaughtyChild()
        => _service.EvaluateRequest(ANaughtyChild())
            .Should()
            .BeFalse();

    [Fact]
    public void RequestIsDeniedForNiceChildWithInfeasibleGift()
        => _service.EvaluateRequest(ANiceChild().ThatWant(AnInfeasibleGift()))
            .Should()
            .BeFalse();
}