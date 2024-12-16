import unittest

from assertpy import assert_that

from system.system import Elf, TaskAssignment


class TaskAssignmentSystemTest(unittest.TestCase):
    def setUp(self):
        elves = [Elf(1, 5), Elf(2, 10), Elf(3, 20)]
        self.system = TaskAssignment(elves)

    def test_report_task_completion_increases_total_tasks_completed(self):
        result = self.system.report_task_completion(1)
        assert_that(result).is_true()
        assert_that(self.system.total_tasks_completed()).is_equal_to(1)

    def test_get_elf_with_highest_skill_returns_the_correct_elf(self):
        highest_skill_elf = self.system.elf_with_highest_skill()
        assert_that(highest_skill_elf.id).is_equal_to(3)

    def test_assign_task_assigns_an_elf_based_on_skill_level(self):
        elf = self.system.assign_task(8)
        assert_that(elf).is_not_none()
        assert_that(elf.id).is_equal_to(2)

    def test_increase_skill_level_updates_elf_skill_correctly(self):
        self.system.increase_skill_level(1, 3)
        elf = self.system.assign_task(7)
        assert_that(elf).is_not_none()
        assert_that(elf.id).is_equal_to(1)

    def test_decrease_skill_level_updates_elf_skill_correctly(self):
        self.system.decrease_skill_level(1, 3)
        self.system.decrease_skill_level(2, 5)
        elf = self.system.assign_task(4)
        assert_that(elf).is_not_none()
        assert_that(elf.id).is_equal_to(2)
        assert_that(elf.skill_level).is_equal_to(5)

    def test_reassign_task_changes_assignment_correctly(self):
        result = self.system.reassign_task(3, 1)
        assert_that(result).is_true()
        elf = self.system.assign_task(19)
        assert_that(elf).is_not_none()
        assert_that(elf.id).is_equal_to(1)

    def test_assign_task_fails_when_skills_required_is_too_high(self):
        elf = self.system.assign_task(50)
        assert_that(elf).is_none()

    def test_list_elves_by_skill_descending_returns_elves_in_correct_order(self):
        sorted_elves = self.system.list_elves_by_skill_descending()
        assert_that([elf.id for elf in sorted_elves]).contains_sequence(3, 2, 1)

    def test_reset_all_skills_to_baseline_resets_all_elves_skills(self):
        self.system.reset_all_skills_to_baseline(10)
        elves = self.system.list_elves_by_skill_descending()
        for elf in elves:
            assert_that(elf.skill_level).is_equal_to(10)

    def test_decrease_skill_level_updates_elf_skill_and_do_not_allow_negative_values(self):
        self.system.decrease_skill_level(1, 10)
        elf = self.system.assign_task(4)
        assert_that(elf).is_not_none()
        assert_that(elf.id).is_equal_to(1)
        assert_that(elf.skill_level).is_equal_to(5)


if __name__ == "__main__":
    unittest.main()
