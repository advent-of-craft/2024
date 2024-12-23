from enum import Enum

from external.stable import MagicStable


class SleighEngineStatus(Enum):
    OFF = 0
    ON = 1


class SleighAction(Enum):
    FLYING = 0
    HOVERING = 1
    PARKED = 2


class AmplifierType(Enum):
    BASIC = 1
    BLESSED = 2
    DIVINE = 3

    def __init__(self, multiplier):
        self.multiplier = multiplier

    def get_multiplier(self):
        return self.multiplier


class Dashboard:
    def display_status(self, message):
        print(message)


class MagicPowerAmplifier:
    def __init__(self, amplifier_type):
        self.amplifier_type = amplifier_type

    def amplify(self, magic_power):
        if magic_power > 0:
            return magic_power * self.amplifier_type.get_multiplier()
        return magic_power


class ReindeersNeedRestException(Exception):
    def __str__(self):
        return "The reindeer needs rest. Please park the sleigh..."


class SleighNotStartedException(Exception):
    def __str__(self):
        return "The sleigh is not started. Please start the sleigh before any other action..."


class ReindeerPowerUnit:
    def __init__(self, reindeer):
        self.reindeer = reindeer
        self.amplifier = MagicPowerAmplifier(AmplifierType.BASIC)

    def harness_magic_power(self):
        if not self.reindeer.needs_rest():
            self.reindeer.times_harnessing += 1
            return self.amplifier.amplify(self.reindeer.get_magic_power())
        return 0

    def check_magic_power(self):
        return self.reindeer.get_magic_power()


class ControlSystem:
    XmasSpirit = 40

    def __init__(self):
        self.dashboard = Dashboard()
        self.magic_stable = MagicStable()
        self.reindeer_power_units = self.bring_all_reindeers()
        self.status = None
        self.action = None
        self.control_magic_power = 0

    def bring_all_reindeers(self):
        return [self.attach_power_unit(reindeer) for reindeer in self.magic_stable.get_all_reindeers()]

    def attach_power_unit(self, reindeer):
        return ReindeerPowerUnit(reindeer)

    def start_system(self):
        self.dashboard.display_status("Starting the sleigh...")
        self.status = SleighEngineStatus.ON
        self.dashboard.display_status("System ready.")

    def ascend(self):
        if self.status == SleighEngineStatus.ON:
            for reindeer_power_unit in self.reindeer_power_units:
                self.control_magic_power += reindeer_power_unit.harness_magic_power()

            if self.check_reindeer_status():
                self.dashboard.display_status("Ascending...")
                self.action = SleighAction.FLYING
                self.control_magic_power = 0
            else:
                raise ReindeersNeedRestException()
        else:
            raise SleighNotStartedException()

    def descend(self):
        if self.status == SleighEngineStatus.ON:
            self.dashboard.display_status("Descending...")
            self.action = SleighAction.HOVERING
        else:
            raise SleighNotStartedException()

    def park(self):
        if self.status == SleighEngineStatus.ON:
            self.dashboard.display_status("Parking...")

            for reindeer_power_unit in self.reindeer_power_units:
                reindeer_power_unit.reindeer.times_harnessing = 0

            self.action = SleighAction.PARKED
        else:
            raise SleighNotStartedException()

    def stop_system(self):
        self.dashboard.display_status("Stopping the sleigh...")
        self.status = SleighEngineStatus.OFF
        self.dashboard.display_status("System shutdown.")

    def check_reindeer_status(self):
        return self.control_magic_power >= self.XmasSpirit
