from typing import Dict, Optional


class Gift:
    def __init__(self, name: str):
        self.name = name

    def __eq__(self, other):
        return isinstance(other, Gift) and self.name == other.name

    def __hash__(self):
        return hash(self.name)


class ManufacturedGift:
    def __init__(self, bar_code: str):
        self.bar_code = bar_code


class Child:
    def __init__(self, name: str):
        self.name = name

    def __eq__(self, other):
        return isinstance(other, Child) and self.name == other.name

    def __hash__(self):
        return hash(self.name)


class Factory(Dict[Gift, ManufacturedGift]):
    def find_manufactured_gift(self, gift: Gift) -> Optional[ManufacturedGift]:
        return self.get(gift)


class Inventory(Dict[str, Gift]):
    def pick_up_gift(self, bar_code: str) -> Optional[Gift]:
        return self.get(bar_code)


class WishList(Dict[Child, Gift]):
    def identify_gift(self, child: Child) -> Optional[Gift]:
        return self.get(child)


class Sleigh(Dict[Child, str]):
    pass


class Business:
    def __init__(self, factory: Factory, inventory: Inventory, wish_list: WishList):
        self.factory = factory
        self.inventory = inventory
        self.wish_list = wish_list

    def load_gifts_in_sleigh(self, *children: Child) -> Sleigh:
        sleigh = Sleigh()
        for child in children:
            gift = self.wish_list.identify_gift(child)
            if gift is not None:
                manufactured_gift = self.factory.find_manufactured_gift(gift)
                if manufactured_gift is not None:
                    final_gift = self.inventory.pick_up_gift(manufactured_gift.bar_code)
                    if final_gift is not None:
                        sleigh[child] = f"Gift: {final_gift.name} has been loaded!"
        return sleigh
