class Reindeer:
    def __init__(self, name, age, spirit, sick=False):
        self.name = name
        self.spirit = spirit
        self.age = age
        self.sick = sick
        self.times_harnessing = 0
        self.power_pull_limit = 5 if age <= 5 else 5 - (age - 5)

    def get_magic_power(self):
        if not self.sick or self.needs_rest():
            if self.age == 1:
                return self.spirit * 0.5
            elif self.age <= 5:
                return self.spirit
            else:
                return self.spirit * 0.25
        return 0

    def needs_rest(self):
        if not self.sick:
            return self.times_harnessing == self.power_pull_limit
        return True
