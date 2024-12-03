class Gift:
    def __init__(self, name: str, weight: float, color: str, material: str):
        self.name = name
        self.weight = weight
        self.color = color
        self.material = material
        self.attributes = {}

    def add_attribute(self, key: str, value: str):
        self.attributes[key] = value

    def get_recommended_age(self) -> int:
        return int(self.attributes.get("recommendedAge", 0))

    def __str__(self):
        return f"A {self.color}-colored {self.name} weighing {self.weight} kg made in {self.material}"
