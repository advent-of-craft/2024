import * as E from 'fp-ts/Either';

export type Step = {
    time: string;
    label: string;
    deliveryTime: number;
};

export class TourCalculator {
    private steps: Step[];
    private calculated: boolean = false;
    private _deliveryTime: number = 0;

    constructor(steps: Step[]) {
        this.steps = steps;
    }

    get deliveryTime(): number {
        return this._deliveryTime;
    }

    calculate(): E.Either<string, string> {
        if (!this.steps || this.steps.length === 0) {
            return E.left('No locations !!!');
        } else {
            const result: string[] = [];

            for (const s of this.steps
                .sort((a, b) => (a.time > b.time ? 1 : -1))) {
                if (!this.calculated) {
                    this._deliveryTime += s.deliveryTime;
                    result.push(this.fLine(s, this._deliveryTime));
                }
            }

            const str = new Date(this._deliveryTime * 1000).toISOString().substring(11);
            result.push(`Delivery time | ${str}`);
            this.calculated = true;

            return E.right(result.join('\n'));
        }
    }

    private fLine(step: Step, x: number): string {
        if (!step) throw new Error('Invalid operation');
        return `${step.time} : ${step.label} | ${step.deliveryTime} sec`;
    }
}