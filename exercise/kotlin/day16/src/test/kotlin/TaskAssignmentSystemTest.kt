import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import system.Elf
import system.TaskAssignment

class TaskAssignmentSystemTest : StringSpec({
    lateinit var system: TaskAssignment

    beforeTest {
        val elves = listOf(Elf(1, 5), Elf(2, 10), Elf(3, 20))
        system = TaskAssignment(elves)
    }

    "reportTaskCompletion increases total tasks completed" {
        system.reportTaskCompletion(1) shouldBe true
        system.totalTasksCompleted() shouldBe 1
    }

    "getElfWithHighestSkill returns the correct elf" {
        val highestSkillElf = system.elfWithHighestSkill()
        highestSkillElf?.id shouldBe 3
    }

    "assignTask assigns an elf based on skill level" {
        val elf = system.assignTask(8)
        elf.shouldNotBeNull()
        elf.id.shouldBe(2)
    }

    "increaseSkillLevel updates elf skill correctly" {
        system.increaseSkillLevel(1, 3)
        val elf = system.assignTask(7)
        elf.shouldNotBeNull()
        elf.id.shouldBe(1)
    }

    "decreaseSkillLevel updates elf skill correctly" {
        system.decreaseSkillLevel(1, 3)
        system.decreaseSkillLevel(2, 5)
        val elf = system.assignTask(4)
        elf.shouldNotBeNull()
        elf.id.shouldBe(2)
        elf.skillLevel.shouldBe(5)
    }

    "reassignTask changes assignment correctly" {
        system.reassignTask(3, 1)
        val elf = system.assignTask(19)
        elf.shouldNotBeNull()
        elf.id.shouldBe(1)
    }

    "assignTask fails when skills required is too high" {
        val elf = system.assignTask(50)
        elf.shouldBeNull()
    }

    "listElvesBySkillDescending returns elves in correct order" {
        val sortedElves = system.listElvesBySkillDescending()
        sortedElves.map { it.id } shouldContainExactly listOf(3, 2, 1)
    }

    "resetAllSkillsToBaseline resets all elves skills to a specified baseline" {
        system.resetAllSkillsToBaseline(10)
        val elves = system.listElvesBySkillDescending()
        elves.forEach { it.skillLevel shouldBe 10 }
    }

    "decreaseSkillLevel updates elf skill and do not allow negative values" {
        system.decreaseSkillLevel(1, 10)
        val elf = system.assignTask(4)
        elf.shouldNotBeNull()
        elf.id.shouldBe(1)
        elf.skillLevel.shouldBe(5)
    }
})