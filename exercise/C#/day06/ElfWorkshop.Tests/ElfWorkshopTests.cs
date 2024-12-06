using FluentAssertions;
using Xunit;

namespace ElfWorkshop.Tests
{
    public class ElfWorkshopTests
    {
        [Fact]
        public void AddTask_Should_Add_Task()
        {
            var workshop = new ElfWorkshop();
            workshop.AddTask("Build toy train");
            workshop.TaskList.Should().Contain("Build toy train");
        }

        [Fact]
        public void AddTask_Should_Add_Craft_Dollhouse_Task()
        {
            var workshop = new ElfWorkshop();
            workshop.AddTask("Craft dollhouse");
            workshop.TaskList.Should().Contain("Craft dollhouse");
        }

        [Fact]
        public void AddTask_Should_Add_Paint_Bicycle_Task()
        {
            var workshop = new ElfWorkshop();
            workshop.AddTask("Paint bicycle");
            workshop.TaskList.Should().Contain("Paint bicycle");
        }

        [Fact]
        public void AddTask_Should_Handle_Empty_Tasks_Correctly()
        {
            var workshop = new ElfWorkshop();
            workshop.AddTask("");
            workshop.TaskList.Should().BeEmpty();
        }
        
        [Fact]
        public void AddTask_Should_Handle_Null_Tasks_Correctly()
        {
            var workshop = new ElfWorkshop();
            workshop.AddTask(null);
            workshop.TaskList.Should().BeEmpty();
        }

        [Fact]
        public void CompleteTask_Should_Remove_Task()
        {
            var workshop = new ElfWorkshop();
            workshop.AddTask("Wrap gifts");
            var removedTask = workshop.CompleteTask();
            removedTask.Should().Be("Wrap gifts");
            workshop.TaskList.Should().BeEmpty();
        }
    }
}