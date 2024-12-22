import {Either, left, right} from 'fp-ts/Either';
import {ParsingException} from "./parsingException";

export class Year {
    private constructor(private readonly value: number) {
    }

    static parse(input: string): Either<ParsingException, Year> {
        const year = parseInt(input);
        if (isNaN(year) || year < 0 || year > 99) {
            return left(new ParsingException('Year should be between 0 and 99'));
        }
        return right(new Year(year));
    }

    toString(): string {
        return this.value.toString().padStart(2, '0');
    }

    equals(other: Year): boolean {
        if (!(other instanceof Year)) {
            return false;
        }
        return this.value === other.value;
    }
}