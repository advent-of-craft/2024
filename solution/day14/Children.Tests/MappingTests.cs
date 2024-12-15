using static Children.Tests.X5T78Mother;

namespace Children.Tests
{
    public class MappingTests
    {
        private readonly ChildMapper _mapper = new();

        [Fact]
        public Task Map_X5T78_To_Girl()
            => Verify(
                _mapper.ToDto(Alice)
            );

        [Fact]
        public Task Map_X5T78_To_Child_For_A_Boy()
            => Verify(
                _mapper.ToDto(Bob)
            );
    }
}