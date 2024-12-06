package workshop

class ElfWorkshop {
    val taskList: MutableList<String> = mutableListOf()

    fun getTaskList(): List<String> {
        return taskList
    }

    fun addTask(task: String?) {
        if (!task.isNullOrEmpty()) {
            taskList.add(task)
        }
    }

    fun completeTask(): String? {
        return if (taskList.isNotEmpty()) {
            taskList.removeAt(0)
        } else {
            null
        }
    }
}