import {Either, left, right} from 'fp-ts/Either';
import {ParsingException} from "./parsingException";

export class SerialNumber {
    private constructor(private readonly value: number) {
    }

    static parse(input: string): Either<ParsingException, SerialNumber> {
        const number = parseInt(input);
        if (isNaN(number) || number < 1 || number > 999) {
            return left(new ParsingException('Serial number should be between 1 and 999'));
        }
        return right(new SerialNumber(number));
    }

    toString(): string {
        return this.value.toString().padStart(3, '0');
    }

    equals(other: SerialNumber): boolean {
        if (!(other instanceof SerialNumber)) {
            return false;
        }
        return this.value === other.value;
    }
}