package workshop

import scala.collection.mutable.ListBuffer

class ElfWorkshop {
  var taskList: ListBuffer[String] = ListBuffer()

  def getTaskList: List[String] = taskList.toList

  def addTask(task: String): Unit = {
    if (Option(task).exists(_.nonEmpty)) {
      taskList += task
    }
  }

  def completeTask(): String = if (taskList.nonEmpty) taskList.remove(0) else null
}