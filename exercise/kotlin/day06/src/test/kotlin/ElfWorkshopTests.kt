import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
import workshop.ElfWorkshop

class ElfWorkshopTests : StringSpec({
    "addTask should add a task" {
        val workshop = ElfWorkshop()
        workshop.addTask("Build toy train")
        workshop.getTaskList() shouldContain "Build toy train"
    }

    "addTask should add Craft dollhouse task" {
        val workshop = ElfWorkshop()
        workshop.addTask("Craft dollhouse")
        workshop.getTaskList() shouldContain "Craft dollhouse"
    }

    "addTask should add Paint bicycle task" {
        val workshop = ElfWorkshop()
        workshop.addTask("Paint bicycle")
        workshop.getTaskList() shouldContain "Paint bicycle"
    }

    "addTask should handle empty tasks correctly" {
        val workshop = ElfWorkshop()
        workshop.addTask("")
        workshop.getTaskList().shouldBeEmpty()
    }

    "addTask should handle null tasks correctly" {
        val workshop = ElfWorkshop()
        workshop.addTask(null)
        workshop.getTaskList().shouldBeEmpty()
    }

    "completeTask should remove task" {
        val workshop = ElfWorkshop()
        workshop.addTask("Wrap gifts")
        val removedTask = workshop.completeTask()
        removedTask shouldBe "Wrap gifts"
        workshop.getTaskList().shouldBeEmpty()
    }
})
