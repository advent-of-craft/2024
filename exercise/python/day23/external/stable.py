from external.deer import Reindeer


class MagicStable:
    def __init__(self):
        self.dasher = Reindeer("Dasher", 4, 10, True)
        self.dancer = Reindeer("Dancer", 2, 8)
        self.prancer = Reindeer("Prancer", 3, 9)
        self.vixen = Reindeer("Vixen", 3, 6)
        self.comet = Reindeer("Comet", 4, 9, True)
        self.cupid = Reindeer("Cupid", 4, 6)
        self.donner = Reindeer("Donner", 7, 6)
        self.blitzen = Reindeer("Blitzen", 8, 7)
        self.rudolph = Reindeer("Rudolph", 6, 3)

    def get_all_reindeers(self):
        return [self.dasher, self.dancer, self.prancer, self.vixen, self.comet, self.cupid, self.donner, self.blitzen,
                self.rudolph]
