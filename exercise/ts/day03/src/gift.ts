import { none, Option, some, exists, match } from 'fp-ts/Option';
import { pipe } from 'fp-ts/function';

const tryParseInt = (string: string): Option<number> => {
    const parsed = Number.parseInt(string, 10);
    return Number.isNaN(parsed) ? none : some(parsed);
}

export class Gift {
    private attributes: Map<string, string> = new Map<string, string>();

    constructor(
        private readonly name: string,
        private readonly weight: number,
        private readonly color: string,
        private readonly material: string,
    ) {
    }

    public assignToChild(childName: string): void {
    }

    public addAttribute(key: string, value: string): void {
        this.attributes.set(key, value);
    }

    public getRecommendedAge(): number {
        return pipe(
            tryParseInt(this.attributes.get('recommendedAge')),
            match(() => 0, recommendedAge => recommendedAge)
        )
    }

    public toString(): string {
        return `A ${this.color}-colored ${this.name} weighing ${this.weight} kg made in ${this.material}`;
    }
}
