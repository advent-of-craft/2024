export class ElfWorkshop {
    taskList: string[] = [];

    addTask(task: string): void {
        if (task !== "") {
            this.taskList.push(task);
        }
    }

    completeTask(): string {
        if (this.taskList.length > 0) {
            return this.taskList.shift();
        }
        return null;
    }
}