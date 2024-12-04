package routine;

import java.util.ArrayList;
import java.util.List;

public class Schedule {
    private List<String> tasks;

    public Schedule() {
        tasks = new ArrayList<>();
    }

    public List<String> getTasks() {
        return tasks;
    }

    public void setTasks(List<String> tasks) {
        this.tasks = tasks;
    }
}
