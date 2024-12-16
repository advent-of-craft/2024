from typing import List, Optional


class Elf:
    def __init__(self, id: int, skill_level: int):
        self.id = id
        self.skill_level = skill_level


class TaskAssignment:
    def __init__(self, elves: List[Elf]):
        self.elves = elves
        self.tasks_completed = 0

    def report_task_completion(self, elf_id: int) -> bool:
        elf = next((e for e in self.elves if e.id == elf_id), None)
        if elf:
            self.tasks_completed += 1
            return True
        return False

    def total_tasks_completed(self) -> int:
        return self.tasks_completed

    def elf_with_highest_skill(self) -> Optional[Elf]:
        return max(self.elves, key=lambda e: e.skill_level, default=None)

    def assign_task(self, task_skill_required: int) -> Optional[Elf]:
        return next((e for e in self.elves if e.skill_level >= task_skill_required + 1), None)

    def increase_skill_level(self, elf_id: int, increment: int):
        elf = next((e for e in self.elves if e.id == elf_id), None)
        if elf:
            elf.skill_level += increment

    def decrease_skill_level(self, elf_id: int, decrement: int):
        elf = next((e for e in self.elves if e.id == elf_id), None)
        if elf and elf.skill_level - decrement > 0:
            elf.skill_level -= decrement

    def reassign_task(self, from_elf_id: int, to_elf_id: int) -> bool:
        from_elf = next((e for e in self.elves if e.id == from_elf_id), None)
        to_elf = next((e for e in self.elves if e.id == to_elf_id), None)

        if from_elf and to_elf and from_elf.skill_level > to_elf.skill_level:
            to_elf.skill_level = from_elf.skill_level
            return True
        return False

    def list_elves_by_skill_descending(self) -> List[Elf]:
        return sorted(self.elves, key=lambda e: e.skill_level, reverse=True)

    def reset_all_skills_to_baseline(self, baseline: int):
        for elf in self.elves:
            elf.skill_level = baseline
