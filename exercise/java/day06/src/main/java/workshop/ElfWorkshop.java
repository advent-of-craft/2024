package workshop;

import java.util.ArrayList;
import java.util.List;

public class ElfWorkshop {
    private List<String> taskList;

    public ElfWorkshop() {
        this.taskList = new ArrayList<>();
    }

    public List<String> getTaskList() {
        return taskList;
    }

    public void addTask(String task) {
        if (task != null && !task.isEmpty()) {
            taskList.add(task);
        }
    }

    public String completeTask() {
        if (!taskList.isEmpty()) {
            return taskList.remove(0);
        }
        return null;
    }
}