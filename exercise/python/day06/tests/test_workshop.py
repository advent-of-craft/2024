import unittest

from workshop.elf_workshop import ElfWorkshop


class TestElfWorkshop(unittest.TestCase):
    def test_add_task_should_add_a_task(self):
        workshop = ElfWorkshop()
        workshop.add_task("Build toy train")
        self.assertIn("Build toy train", workshop.task_list)

    def test_checks_for_task_addition_craft_dollhouse(self):
        workshop = ElfWorkshop()
        workshop.add_task("Craft dollhouse")
        self.assertTrue("Craft dollhouse" in workshop.task_list)

    def test_checks_for_task_addition_paint_bicycle(self):
        workshop = ElfWorkshop()
        workshop.add_task("Paint bicycle")
        self.assertTrue("Paint bicycle" in workshop.task_list)

    def test_should_handle_empty_tasks_correctly(self):
        workshop = ElfWorkshop()
        workshop.add_task("")
        self.assertEqual(len(workshop.task_list), 0)

    def test_task_removal_functionality(self):
        workshop = ElfWorkshop()
        workshop.add_task("Wrap gifts")
        removed_task = workshop.complete_task()
        self.assertEqual(removed_task, "Wrap gifts")
        self.assertEqual(len(workshop.task_list), 0)


if __name__ == "__main__":
    unittest.main()
