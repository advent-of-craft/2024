from typing import List, Optional

class Toy:
    def __init__(self, description: str):
        self.description = description

    def __eq__(self, other):
        return isinstance(other, Toy) and self.description == other.description

    def __repr__(self):
        return f"Toy(description='{self.description}')"


class Child:
    def __init__(self, name: str, behavior: str):
        self.name = name
        self.behavior = behavior
        self.wishlist: List[Toy] = []

    def set_wishlist(self, first_choice: Toy, second_choice: Toy, third_choice: Toy):
        self.wishlist = [first_choice, second_choice, third_choice]


class Santa:
    def __init__(self):
        self._children_repository: List[Child] = []

    def choose_toy_for_child(self, child_name: str) -> Optional[Toy]:
        found_child = next((child for child in self._children_repository if child.name == child_name), None)

        if found_child is None:
            raise ValueError("No such child found")

        if found_child.behavior == "naughty":
            return found_child.wishlist[-1]
        elif found_child.behavior == "nice":
            return found_child.wishlist[1]
        elif found_child.behavior == "very nice":
            return found_child.wishlist[0]
        return None

    def add_child(self, child: Child):
        self._children_repository.append(child)
