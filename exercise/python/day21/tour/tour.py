from datetime import time, timedelta
from typing import List, Union


class Step:
    def __init__(self, time: time, label: str, delivery_time: int):
        self.time = time
        self.label = label
        self.delivery_time = delivery_time


class TourCalculator:
    def __init__(self, steps: List[Step]):
        self.steps = steps
        self.calculated = False
        self.delivery_time = 0.0

    def calculate(self) -> Union[str, str]:
        if not self.steps:
            return "No locations !!!"

        result = []

        for step in sorted(self.steps, key=lambda s: s.time):
            if not self.calculated:
                self.delivery_time += step.delivery_time
                result.append(self.f_line(step, self.delivery_time))

        duration_str = self.format_duration_to_hhmmss(
            timedelta(seconds=int(self.delivery_time))
        )
        result.append(f"Delivery time | {duration_str}")
        self.calculated = True

        return "\n".join(result)

    def format_duration_to_hhmmss(self, duration: timedelta) -> str:
        hours, remainder = divmod(duration.seconds, 3600)
        minutes, seconds = divmod(remainder, 60)
        return f"{hours:02}:{minutes:02}:{seconds:02}"

    def f_line(self, step: Step, x: float) -> str:
        if step is not None:
            return f"{step.time} : {step.label} | {step.delivery_time} sec"
        else:
            raise ValueError("Step is None")
