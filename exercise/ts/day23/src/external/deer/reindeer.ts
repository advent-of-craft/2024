export class Reindeer {
    private spirit = 0;
    private age = 0;
    private name: string;
    public sick = false;
    public timesHarnessing = 0;
    private powerPullLimit = 0;

    constructor(name: string, age: number, spirit: number, sick: boolean = false) {
        this.name = name;
        this.spirit = spirit;
        this.age = age;
        this.sick = sick;
        this.powerPullLimit = age <= 5 ? 5 : 5 - (age - 5);
    }

    public getMagicPower(): number {
        if (!this.sick || this.needsRest()) {
            if (this.age === 1) return this.spirit * 0.5;
            if (this.age <= 5) return this.spirit;
            return this.spirit * 0.25;
        }
        return 0;
    }

    public needsRest(): boolean {
        if (!this.sick) {
            return this.timesHarnessing === this.powerPullLimit;
        }
        return true;
    }
}
