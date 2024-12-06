package workshop;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ElfWorkshopTests {
    @Test
    public void addTask_Should_Add_Task() {
        var workshop = new ElfWorkshop();
        workshop.addTask("Build toy train");
        assertThat(workshop.getTaskList()).contains("Build toy train");
    }

    @Test
    public void addTask_Should_Add_Craft_Dollhouse_Task() {
        var workshop = new ElfWorkshop();
        workshop.addTask("Craft dollhouse");
        assertThat(workshop.getTaskList()).contains("Craft dollhouse");
    }

    @Test
    public void addTask_Should_Add_Paint_Bicycle_Task() {
        var workshop = new ElfWorkshop();
        workshop.addTask("Paint bicycle");
        assertThat(workshop.getTaskList()).contains("Paint bicycle");
    }

    @Test
    public void addTask_Should_Handle_Empty_Tasks_Correctly() {
        var workshop = new ElfWorkshop();
        workshop.addTask("");
        assertThat(workshop.getTaskList()).isEmpty();
    }

    @Test
    public void addTask_Should_Handle_Null_Tasks_Correctly() {
        var workshop = new ElfWorkshop();
        workshop.addTask(null);
        assertThat(workshop.getTaskList()).isEmpty();
    }

    @Test
    public void completeTask_Should_Remove_Task() {
        var workshop = new ElfWorkshop();
        workshop.addTask("Wrap gifts");
        var removedTask = workshop.completeTask();
        assertThat(removedTask).isEqualTo("Wrap gifts");
        assertThat(workshop.getTaskList()).isEmpty();
    }
}
