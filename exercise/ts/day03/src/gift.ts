export class Gift {
  private readonly attributes: Map<string, string> = new Map<string, string>();

  constructor(
    private readonly name: string,
    private readonly weight: number,
    private readonly color: string,
    private readonly material: string
  ) {}

  public getName(): string {
    return this.name;
  }

  public getWeight(): number {
    return this.weight;
  }

  public getColor(): string {
    return this.color;
  }

  public getMaterial(): string {
    return this.material;
  }

  public assignToChild(childName: string): void {
    this.addAttribute("assignedTo", childName);
  }

  public addAttribute(key: string, value: string): void {
    this.attributes.set(key, value);
  }

  public getAttribute(key: string): string | undefined {
    return this.attributes.get(key);
  }

  public getRecommendedAge(): number {
    const recommendedAge = this.attributes.get("recommendedAge");
    return recommendedAge ? parseInt(recommendedAge, 10) : 0;
  }

  public toString(): string {
    return `A ${this.color}-colored ${this.name} weighing ${this.weight} kg made in ${this.material}`;
  }
}
