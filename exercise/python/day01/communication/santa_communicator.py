class SantaCommunicator:
    def __init__(self, number_of_days_to_rest):
        self.number_of_days_to_rest = number_of_days_to_rest

    def compose_message(self, reindeer_name, current_location, numbers_of_days_for_coming_back,
                        number_of_days_before_christmas):
        days_before_return = self._days_before_return(numbers_of_days_for_coming_back, number_of_days_before_christmas)
        return f"Dear {reindeer_name}, please return from {current_location} in {days_before_return} day(s) to be ready and rest before Christmas."

    def is_overdue(self, reindeer_name, current_location, numbers_of_days_for_coming_back,
                   number_of_days_before_christmas, logger):
        if self._days_before_return(numbers_of_days_for_coming_back, number_of_days_before_christmas) <= 0:
            logger.log(f"Overdue for {reindeer_name} located {current_location}.")
            return True
        return False

    def _days_before_return(self, numbers_of_days_for_coming_back, number_of_days_before_christmas):
        return number_of_days_before_christmas - numbers_of_days_for_coming_back - self.number_of_days_to_rest
