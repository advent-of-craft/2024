using ControlSystem.Core;
using FluentAssertions;

namespace ControlSystem.Tests
{
    public class TestControlSystem : IDisposable
    {
        private readonly StringWriter _output;
        private readonly TextWriter _originalOutput;

        public TestControlSystem()
        {
            _output = new StringWriter();
            _originalOutput = Console.Out;
            Console.SetOut(_output);
        }

        [Fact]
        public void TestStart()
        {
            // The system has been started
            var controlSystem = new Core.System();
            controlSystem.Action = SleighAction.Flying;
            controlSystem.Status = SleighEngineStatus.Off;
            controlSystem.StartSystem();
            controlSystem.Status.Should().Be(SleighEngineStatus.On);
            _output.ToString().Trim().Should().Be("Starting the sleigh...\r\nSystem ready.");
        }

        [Fact]
        public void TestAscend()
        {
            var controlSystem = new Core.System();
            controlSystem.StartSystem();
            controlSystem.Invoking(cs => cs.Ascend()).Should().NotThrow<ReindeersNeedRestException>();
            controlSystem.Action.Should().Be(SleighAction.Flying);
            _output.ToString().Trim().Should().Be("Starting the sleigh...\r\nSystem ready.\r\nAscending...");
        }

        [Fact]
        public void TestDescend()
        {
            var controlSystem = new Core.System();
            controlSystem.StartSystem();
            controlSystem.Ascend();
            controlSystem.Invoking(cs => cs.Descend()).Should().NotThrow<SleighNotStartedException>();
            controlSystem.Action.Should().Be(SleighAction.Hovering);
            _output.ToString().Trim().Should().Be("Starting the sleigh...\r\nSystem ready.\r\nAscending...\r\nDescending...");
        }

        [Fact]
        public void TestPark()
        {
            var controlSystem = new Core.System();
            controlSystem.StartSystem();
            controlSystem.Invoking(cs => cs.Park()).Should().NotThrow<SleighNotStartedException>();
            controlSystem.Action.Should().Be(SleighAction.Parked);
        }

        public void Dispose()
        {
            Console.SetOut(_originalOutput);
            _output.Dispose();
        }
    }
}