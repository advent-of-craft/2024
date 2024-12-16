package system

case class Elf(id: Int, var skillLevel: Int)

class TaskAssignment(private val elves: List[Elf]) {
  private var tasksCompleted: Int = 0

  def reportTaskCompletion(elfId: Int): Boolean = {
    elves.find(_.id == elfId) match {
      case Some(_) =>
        tasksCompleted += 1
        true
      case None => false
    }
  }

  def totalTasksCompleted(): Int = tasksCompleted

  def elfWithHighestSkill(): Option[Elf] = elves.maxByOption(_.skillLevel)

  def assignTask(taskSkillRequired: Int): Option[Elf] =
    elves.find(_.skillLevel >= taskSkillRequired + 1)

  def increaseSkillLevel(elfId: Int, increment: Int): Unit = {
    elves.find(_.id == elfId).foreach { elf =>
      elf.skillLevel += increment
    }
  }

  def decreaseSkillLevel(elfId: Int, decrement: Int): Unit = {
    elves.find(_.id == elfId).foreach { elf =>
      if (elf.skillLevel - decrement > 0) {
        elf.skillLevel -= decrement
      }
    }
  }

  def reassignTask(fromElfId: Int, toElfId: Int): Boolean = {
    (elves.find(_.id == fromElfId), elves.find(_.id == toElfId)) match {
      case (Some(fromElf), Some(toElf)) if fromElf.skillLevel > toElf.skillLevel =>
        toElf.skillLevel = fromElf.skillLevel
        true
      case _ => false
    }
  }

  def listElvesBySkillDescending(): List[Elf] = elves.sortBy(-_.skillLevel)

  def resetAllSkillsToBaseline(baseline: Int): Unit = elves.foreach(_.skillLevel = baseline)
}