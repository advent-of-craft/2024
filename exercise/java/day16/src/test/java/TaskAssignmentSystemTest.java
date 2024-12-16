import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import system.Elf;
import system.TaskAssignment;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

class TaskAssignmentSystemTest {
    private TaskAssignment system;

    @BeforeEach
    void setUp() {
        List<Elf> elves = asList(
                new Elf(1, 5),
                new Elf(2, 10),
                new Elf(3, 20)
        );
        system = new TaskAssignment(elves);
    }

    @Test
    void reportTaskCompletionIncreasesTotalTasksCompleted() {
        assertThat(system.reportTaskCompletion(1)).isTrue();
        assertThat(system.getTotalTasksCompleted()).isEqualTo(1);
    }

    @Test
    void getElfWithHighestSkillReturnsTheCorrectElf() {
        var highestSkillElf = system.getElfWithHighestSkill();
        assertThat(highestSkillElf.getId()).isEqualTo(3);
    }

    @Test
    void assignTaskAssignsAnElfBasedOnSkillLevel() {
        var elf = system.assignTask(8);

        assertThat(elf).isNotNull();
        assertThat(elf.getId()).isEqualTo(2);
    }

    @Test
    void increaseSkillLevelUpdatesElfSkillCorrectly() {
        system.increaseSkillLevel(1, 3);

        var elf = system.assignTask(7);

        assertThat(elf).isNotNull();
        assertThat(elf.getId()).isEqualTo(1);
    }

    @Test
    void decreaseSkillLevelUpdatesElfSkillCorrectly() {
        system.decreaseSkillLevel(1, 3);
        system.decreaseSkillLevel(2, 5);

        var elf = system.assignTask(4);

        assertThat(elf).isNotNull();
        assertThat(elf.getId()).isEqualTo(2);
        assertThat(elf.getSkillLevel()).isEqualTo(5);
    }

    @Test
    void reassignTaskChangesAssignmentCorrectly() {
        system.reassignTask(3, 1);
        var elf = system.assignTask(19);

        assertThat(elf).isNotNull();
        assertThat(elf.getId()).isEqualTo(1);
    }

    @Test
    void assignTaskFailsWhenSkillsRequiredIsTooHigh() {
        assertThat(system.assignTask(50))
                .isNull();
    }

    @Test
    void listElvesBySkillDescendingReturnsElvesInCorrectOrder() {
        var sortedElves = system.listElvesBySkillDescending();

        assertThat(sortedElves)
                .extracting(Elf::getId)
                .containsExactly(3, 2, 1);
    }

    @Test
    void resetAllSkillsToBaselineResetsAllElvesSkillsToASpecifiedBaseline() {
        system.resetAllSkillsToBaseline(10);
        var elves = system.listElvesBySkillDescending();

        for (Elf elf : elves) {
            assertThat(elf.getSkillLevel()).isEqualTo(10);
        }
    }

    @Test
    void decreaseSkillLevelUpdatesElfSkillAndDoNotAllowNegativeValues() {
        system.decreaseSkillLevel(1, 10);
        var elf = system.assignTask(4);

        assertThat(elf).isNotNull();
        assertThat(elf.getId()).isEqualTo(1);
        assertThat(elf.getSkillLevel()).isEqualTo(5);
    }
}
