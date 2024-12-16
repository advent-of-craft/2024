package system

data class Elf(val id: Int, var skillLevel: Int)

class TaskAssignment(private val elves: List<Elf>) {
    private var tasksCompleted: Int = 0

    fun reportTaskCompletion(elfId: Int): Boolean {
        val elf = elves.find { it.id == elfId }
        if (elf != null) {
            tasksCompleted++
            return true
        }
        return false
    }

    fun totalTasksCompleted(): Int = tasksCompleted

    fun elfWithHighestSkill(): Elf? = elves.maxByOrNull { it.skillLevel }

    fun assignTask(taskSkillRequired: Int): Elf? =
        elves.find { it.skillLevel >= taskSkillRequired + 1 }

    fun increaseSkillLevel(elfId: Int, increment: Int) {
        elves.find { it.id == elfId }
            ?.let {
                it.skillLevel += increment
            }
    }

    fun decreaseSkillLevel(elfId: Int, decrement: Int) {
        elves.find { it.id == elfId }
            ?.let {
                if (it.skillLevel - decrement > 0) {
                    it.skillLevel -= decrement
                }
            }
    }

    fun reassignTask(fromElfId: Int, toElfId: Int): Boolean {
        val fromElf = elves.find { it.id == fromElfId }
        val toElf = elves.find { it.id == toElfId }

        return if (fromElf != null && toElf != null && fromElf.skillLevel > toElf.skillLevel) {
            toElf.skillLevel = fromElf.skillLevel
            true
        } else false
    }

    fun listElvesBySkillDescending(): List<Elf> = elves.sortedByDescending { it.skillLevel }
    fun resetAllSkillsToBaseline(baseline: Int) = elves.forEach { it.skillLevel = baseline }
}