using FluentAssertions;
using Xunit;

namespace EID.Tests
{
    public class EIDTests
    {
        [Fact]
        public void A_First_Test() 
            => 43.Should()
                .Be(42, "it is universal answer");
    }
}