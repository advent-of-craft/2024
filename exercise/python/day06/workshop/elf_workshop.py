class ElfWorkshop:
    def __init__(self):
        self.task_list = []

    def get_task_list(self):
        return self.task_list

    def add_task(self, task):
        if task:
            self.task_list.append(task)

    def complete_task(self):
        if self.task_list:
            return self.task_list.pop(0)
        return None
