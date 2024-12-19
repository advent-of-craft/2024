export class OverflowException extends Error {
    constructor(message: string) {
        super(message);
        this.name = "CustomOverflowException";
    }
}

export class SantaTravelCalculator {
    public static calculateTotalDistanceRecursively(numberOfReindeers: number): number {
        if(numberOfReindeers >= 32)
            throw new OverflowException(`Overflow for ${numberOfReindeers} reindeers`);

        if (numberOfReindeers === 1) return 1;

        return 2 * this.calculateTotalDistanceRecursively(numberOfReindeers - 1) + 1;
    }
}