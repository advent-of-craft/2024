import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import workshop.ElfWorkshop

class ElfWorkshopSpec extends AnyFunSuite with Matchers {

  test("addTask should add a task") {
    val workshop = new ElfWorkshop()
    workshop.addTask("Build toy train")
    workshop.getTaskList should contain("Build toy train")
  }

  test("addTask should add Craft dollhouse task") {
    val workshop = new ElfWorkshop()
    workshop.addTask("Craft dollhouse")
    workshop.getTaskList should contain("Craft dollhouse")
  }

  test("addTask should add Paint bicycle task") {
    val workshop = new ElfWorkshop()
    workshop.addTask("Paint bicycle")
    workshop.getTaskList should contain("Paint bicycle")
  }

  test("addTask should handle empty tasks correctly") {
    val workshop = new ElfWorkshop()
    workshop.addTask("")
    workshop.getTaskList should be(empty)
  }

  test("addTask should handle null tasks correctly") {
    val workshop = new ElfWorkshop()
    workshop.addTask(null)
    workshop.getTaskList should be(empty)
  }

  test("completeTask should remove task") {
    val workshop = new ElfWorkshop()
    workshop.addTask("Wrap gifts")
    val removedTask = workshop.completeTask()
    removedTask should be("Wrap gifts")
    workshop.getTaskList should be(empty)
  }
}