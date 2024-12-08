from domain.toy import Toy, ToyRepository


class InMemoryToyRepository(ToyRepository):
    def __init__(self):
        self.toys = []

    def find_by_name(self, name: str) -> Toy:
        return next((toy for toy in self.toys if toy.name == name), None)

    def save(self, toy: Toy):
        self.toys = [t for t in self.toys if t.name != toy.name]
        self.toys.append(toy)
