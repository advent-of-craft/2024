from preparation.Gift import Gift


class SantaWorkshopService:
    def __init__(self):
        self.prepared_gifts = []

    def prepare_gift(self, gift_name: str, weight: float, color: str, material: str) -> Gift:
        if weight > 5:
            raise ValueError("Gift is too heavy for Santa's sleigh")

        gift = Gift(gift_name, weight, color, material)
        self.prepared_gifts.append(gift)
        return gift
