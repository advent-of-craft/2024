import {Either, isLeft, left, right, chain} from 'fp-ts/Either';
import {Sex} from "./sex";
import {Year} from "./year";
import {SerialNumber} from "./serialNumber";
import {ParsingException} from "./parsingException";
import {pipe} from "fp-ts/function";

export class EID {
    constructor(
        private readonly sex: Sex,
        private readonly year: Year,
        private readonly serialNumber: SerialNumber
    ) {
    }

    static parse(potentialEID: string): Either<ParsingException, EID> {
        return pipe(
            Sex.parse(potentialEID[0]),
            chain(sex =>
                pipe(
                    Year.parse(potentialEID.substring(1, 3)),
                    chain(year =>
                        pipe(
                            SerialNumber.parse(potentialEID.substring(3, 6)),
                            chain(serialNumber => {
                                const eid = new EID(sex, year, serialNumber);
                                return eid.checkKey(potentialEID.substring(6))
                                    ? right(eid)
                                    : left(new ParsingException('Invalid key'));
                            })
                        )
                    )
                )
            )
        );
    }

    private checkKey(potentialKey: string): boolean {
        const key = parseInt(potentialKey);
        return !isNaN(key) && this.key() === key;
    }

    private stringWithoutKey(): string {
        return `${this.sex}${this.year}${this.serialNumber}`;
    }

    private toLong(): number {
        return parseInt(this.sex.toString() + this.year + this.serialNumber.toString());
    }

    key(): number {
        return 97 - (this.toLong() % 97);
    }

    toString(): string {
        return this.stringWithoutKey() + this.key().toString().padStart(2, '0');
    }

    equals(other: EID): boolean {
        if (!(other instanceof EID)) {
            return false;
        }
        return this.sex === other.sex &&
            this.year.equals(other.year) &&
            this.serialNumber.equals(other.serialNumber);
    }
}