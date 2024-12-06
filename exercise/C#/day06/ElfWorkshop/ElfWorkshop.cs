namespace ElfWorkshop;

public class ElfWorkshop
{
    public List<string> TaskList { get; } = [];

    public void AddTask(string task)
    {
        if (task != null && task != "")
        {
            TaskList.Add(task);
        }
    }

    public string CompleteTask()
    {
        if (TaskList.Count > 0)
        {
            var task = TaskList[0];
            TaskList.RemoveAt(0);
            return task;
        }

        return null;
    }
}