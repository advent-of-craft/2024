class SantaTravelCalculator:
    @staticmethod
    def calculate_total_distance_recursively(number_of_reindeers: int) -> int:
        if number_of_reindeers == 1:
            return 1
        if number_of_reindeers == 32:
            raise ArithmeticError("Integer overflow")

        result = 2 * SantaTravelCalculator.calculate_total_distance_recursively(number_of_reindeers - 1) + 1
        return result
