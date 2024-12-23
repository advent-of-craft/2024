using ControlSystem.Core;
using ControlSystem.External;
using FluentAssertions;

namespace ControlSystem.Tests;

public class TestControlSystem : IDisposable
{
    private readonly StringWriter _output;
    private readonly TextWriter _originalOutput;
    private readonly Core.System _controlSystem;
    
    public TestControlSystem()
    {
        _output = new StringWriter();
        _originalOutput = Console.Out;
        Console.SetOut(_output);
        
        var magicStable = new MagicStable();
        // reset the reindeer as Healthy for test
        _controlSystem = new Core.System(magicStable);
    }

    [Fact]
    public void TestStart()
    {
        // The system has been started
        _controlSystem.Action = SleighAction.Flying;
        _controlSystem.Status = SleighEngineStatus.Off;
        _controlSystem.StartSystem();
        _controlSystem.Status.Should().Be(SleighEngineStatus.On);
        _output.ToString().Trim().Should().Be($"Starting the sleigh...{Environment.NewLine}System ready.");
    }

    [Fact]
    public void TestAscend()
    {
        _controlSystem.StartSystem();
        _controlSystem.Invoking(cs => cs.Ascend()).Should().NotThrow<ReindeersNeedRestException>();
        _controlSystem.Action.Should().Be(SleighAction.Flying);
        _output.ToString().Trim().Should().Be($"Starting the sleigh...{Environment.NewLine}System ready.{Environment.NewLine}Ascending...");
    }

    [Fact]
    public void TestDescend()
    {
        _controlSystem.StartSystem();
        _controlSystem.Ascend();
        _controlSystem.Invoking(cs => cs.Descend()).Should().NotThrow<SleighNotStartedException>();
        _controlSystem.Action.Should().Be(SleighAction.Hovering);
        _output.ToString().Trim().Should().Be($"Starting the sleigh...{Environment.NewLine}System ready.{Environment.NewLine}Ascending...{Environment.NewLine}Descending...");
    }

    [Fact]
    public void TestPark()
    {
        _controlSystem.StartSystem();
        _controlSystem.Invoking(cs => cs.Park()).Should().NotThrow<SleighNotStartedException>();
        _controlSystem.Action.Should().Be(SleighAction.Parked);
    }

    public void Dispose()
    {
        Console.SetOut(_originalOutput);
        _output.Dispose();
    }
}