# routine.py

from abc import ABC, abstractmethod


class Email:
    def __init__(self, subject: str, body: str):
        self.subject = subject
        self.body = body


class EmailService(ABC):
    @abstractmethod
    def read_new_emails(self):
        pass


class ReindeerFeeder(ABC):
    @abstractmethod
    def feed_reindeers(self):
        pass


class Schedule:
    def __init__(self):
        self.tasks = []


class ScheduleService(ABC):
    @abstractmethod
    def today_schedule(self) -> Schedule:
        pass

    @abstractmethod
    def organize_my_day(self, schedule: Schedule):
        pass

    @abstractmethod
    def continue_day(self):
        pass


class Routine:
    def __init__(self, email_service: EmailService, schedule_service: ScheduleService, reindeer_feeder: ReindeerFeeder):
        self.email_service = email_service
        self.schedule_service = schedule_service
        self.reindeer_feeder = reindeer_feeder

    def start(self):
        self.schedule_service.organize_my_day(self.schedule_service.today_schedule())
        self.reindeer_feeder.feed_reindeers()
        self.email_service.read_new
