import sys
import unittest
from io import StringIO

from assertpy import assert_that

from core.control import ControlSystem, SleighAction, SleighEngineStatus


class TestControlSystem(unittest.TestCase):
    def setUp(self):
        self.held_output = StringIO()
        sys.stdout = self.held_output

    def tearDown(self):
        self.held_output.close()
        sys.stdout = sys.__stdout__

    def test_start(self):
        # The system has been started
        control_system = ControlSystem()
        control_system.action = SleighAction.FLYING
        control_system.status = SleighEngineStatus.OFF
        control_system.start_system()
        assert_that(control_system.status).is_equal_to(SleighEngineStatus.ON)
        assert_that(self.held_output.getvalue().strip()).is_equal_to("Starting the sleigh...\nSystem ready.")

    def test_ascend(self):
        control_system = ControlSystem()
        control_system.start_system()
        control_system.ascend()
        assert_that(control_system.action).is_equal_to(SleighAction.FLYING)
        assert_that(self.held_output.getvalue().strip()).is_equal_to(
            "Starting the sleigh...\nSystem ready.\nAscending...")

    def test_descend(self):
        control_system = ControlSystem()
        control_system.start_system()
        control_system.ascend()
        control_system.descend()
        assert_that(control_system.action).is_equal_to(SleighAction.HOVERING)
        assert_that(self.held_output.getvalue().strip()).is_equal_to(
            "Starting the sleigh...\nSystem ready.\nAscending...\nDescending...")

    def test_park(self):
        control_system = ControlSystem()
        control_system.start_system()
        control_system.park()
        assert_that(control_system.action).is_equal_to(SleighAction.PARKED)


if __name__ == "__main__":
    unittest.main()
