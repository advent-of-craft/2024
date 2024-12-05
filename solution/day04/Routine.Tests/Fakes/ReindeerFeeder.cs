using FluentAssertions;

namespace Routine.Tests.Fakes;

public class ReindeerFeeder : IReindeerFeeder
{
    private bool _hasBeenFed;
    public void FeedReindeers() => _hasBeenFed = true;
    public void HasBeenFed() => _hasBeenFed.Should().BeTrue();
}