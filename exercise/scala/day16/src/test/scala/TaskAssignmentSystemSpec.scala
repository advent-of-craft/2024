import org.scalatest.BeforeAndAfterEach
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import system.{Elf, TaskAssignment}

class TaskAssignmentSystemSpec extends AnyFunSuite with Matchers with BeforeAndAfterEach {
  var system: TaskAssignment = _

  override def beforeEach(): Unit = {
    val elves = List(Elf(1, 5), Elf(2, 10), Elf(3, 20))
    system = new TaskAssignment(elves)
  }

  test("reportTaskCompletion increases total tasks completed") {
    system.reportTaskCompletion(1) shouldBe true
    system.totalTasksCompleted() shouldBe 1
  }

  test("getElfWithHighestSkill returns the correct elf") {
    val highestSkillElf = system.elfWithHighestSkill()
    highestSkillElf.map(_.id) shouldBe Some(3)
  }

  test("assignTask assigns an elf based on skill level") {
    val elf = system.assignTask(8)
    elf should not be empty
    elf.map(_.id) shouldBe Some(2)
  }

  test("increaseSkillLevel updates elf skill correctly") {
    system.increaseSkillLevel(1, 3)
    val elf = system.assignTask(7)
    elf should not be empty
    elf.map(_.id) shouldBe Some(1)
  }

  test("decreaseSkillLevel updates elf skill correctly") {
    system.decreaseSkillLevel(1, 3)
    system.decreaseSkillLevel(2, 5)
    val elf = system.assignTask(4)
    elf should not be empty
    elf.map(_.id) shouldBe Some(2)
    elf.map(_.skillLevel) shouldBe Some(5)
  }

  test("reassignTask changes assignment correctly") {
    system.reassignTask(3, 1)
    val elf = system.assignTask(19)
    elf should not be empty
    elf.map(_.id) shouldBe Some(1)
  }

  test("assignTask fails when skills required is too high") {
    val elf = system.assignTask(50)
    elf shouldBe empty
  }

  test("listElvesBySkillDescending returns elves in correct order") {
    val sortedElves = system.listElvesBySkillDescending()
    sortedElves.map(_.id) shouldBe List(3, 2, 1)
  }

  test("resetAllSkillsToBaseline resets all elves skills to a specified baseline") {
    system.resetAllSkillsToBaseline(10)
    val elves = system.listElvesBySkillDescending()
    elves.foreach(_.skillLevel shouldBe 10)
  }

  test("decreaseSkillLevel updates elf skill and do not allow negative values") {
    system.decreaseSkillLevel(1, 10)
    val elf = system.assignTask(4)
    elf should not be empty
    elf.map(_.id) shouldBe Some(1)
    elf.map(_.skillLevel) shouldBe Some(5)
  }
}