from datetime import datetime


class Time:
    now = datetime(2024, 12, 24, 23, 30, 45)

    @staticmethod
    def time_provider():
        return Time.now
