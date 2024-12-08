# service.py

from domain.toy import ToyRepository, State


class ToyProductionService:
    def __init__(self, repository: ToyRepository):
        self.repository = repository

    def assign_toy_to_elf(self, toy_name: str):
        toy = self.repository.find_by_name(toy_name)
        if toy and toy.state == State.UNASSIGNED:
            toy.state = State.IN_PRODUCTION
            self.repository.save(toy)
