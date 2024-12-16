using FluentAssertions;
using Xunit;

namespace TaskAssignmentSystem.Tests;

public class TaskAssignmentTests
{
    private readonly TaskAssignment _system;

    public TaskAssignmentTests()
    {
        var elves = new List<Elf>
        {
            new(1, 5),
            new(2, 10),
            new(3, 20)
        };
        _system = new TaskAssignment(elves);
    }

    [Fact]
    public void ReportTaskCompletion_IncreasesTotalTasksCompleted()
    {
        _system.ReportTaskCompletion(1).Should().BeTrue();
        _system.TotalTasksCompleted.Should().Be(1);
    }

    [Fact]
    public void GetElfWithHighestSkill_ReturnsCorrectElf()
    {
        var highestSkillElf = _system.ElfWithHighestSkill();
        highestSkillElf.Id.Should().Be(3);
    }

    [Fact]
    public void AssignTask_AssignsElfBasedOnSkillLevel()
    {
        _system.AssignTask(8).Should().BeEquivalentTo(new Elf(2, 10));
    }

    [Fact]
    public void IncreaseSkillLevel_UpdatesElfSkillCorrectly()
    {
        _system.IncreaseSkillLevel(1, 3);
        var elf = _system.AssignTask(7);
        elf.Id.Should().Be(1);
    }

    [Fact]
    public void DecreaseSkillLevel_UpdatesElfSkillCorrectly()
    {
        _system.DecreaseSkillLevel(1, 3);
        _system.DecreaseSkillLevel(2, 5);

        var elf = _system.AssignTask(4);
        elf.Id.Should().Be(2);
        elf.SkillLevel.Should().Be(5);
    }

    [Fact]
    public void AssignTaskBasedOnAvailability_AssignsAvailableElf()
    {
        var elf = _system.AssignTaskBasedOnAvailability(10);
        elf.Should().NotBeNull();
    }

    [Fact]
    public void ReassignTask_ChangesAssignmentCorrectly()
    {
        _system.ReassignTask(3, 1);
        var elf = _system.AssignTask(19);
        elf.Id.Should().Be(1);
    }

    [Fact]
    public void AssignTask_FailsWhenSkillsRequiredIsTooHigh()
    {
        _system.AssignTask(50).Should().BeNull();
    }

    [Fact]
    public void ListElvesBySkillDescending_ReturnsElvesInCorrectOrder()
    {
        var sortedElves = _system.ElvesBySkillDescending();
        sortedElves.ConvertAll(elf => elf.Id).Should().Equal(new List<int> {3, 2, 1});
    }

    [Fact]
    public void ResetAllSkillsToBaseline_ResetsAllElvesSkillsToSpecifiedBaseline()
    {
        _system.ResetAllSkillsToBaseline(10);
        var elves = _system.ElvesBySkillDescending();
        foreach (var elf in elves)
        {
            elf.SkillLevel.Should().Be(10);
        }
    }

    [Fact]
    public void DecreaseSkillLevel_UpdatesElfSkillAndDoesNotAllowNegativeValues()
    {
        _system.DecreaseSkillLevel(1, 10);
        var elf = _system.AssignTask(4);
        elf.Id.Should().Be(1);
        elf.SkillLevel.Should().Be(5);
    }
}