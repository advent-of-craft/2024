import * as fc from 'fast-check';
import {sample} from 'fast-check';
import {Sex} from "../src/eid/sex";
import {Year} from "../src/eid/year";
import {SerialNumber} from "../src/eid/serialNumber";
import {EID} from "../src/eid/eid";
import {isLeft, isRight} from "fp-ts/Either";

const yearGenerator = fc.integer()
    .filter(x => x >= 0 && x <= 99)
    .map(x => Year.parse(x.toString()))
    .map(year => {
        if (isRight(year)) {
            return year.right;
        }
    });

const serialNumberGenerator = fc.integer()
    .filter(x => x >= 1 && x <= 999)
    .map(x => SerialNumber.parse(x.toString()))
    .map(serialNumber => {
        if (isRight(serialNumber)) {
            return serialNumber.right;
        }
    });

const eidGenerator = fc.record({
    sex: fc.constantFrom(Sex.Sloubi, Sex.Gagna, Sex.Catact),
    year: yearGenerator,
    serialNumber: serialNumberGenerator,
}).map(({sex, year, serialNumber}) => new EID(sex, year, serialNumber));

describe('EID', () => {
    test('round tripping', () => {
        fc.assert(
            fc.property(eidGenerator, validEID => {
                const parsedEID = EID.parse(validEID.toString());
                expect(isRight(parsedEID)).toBe(true);

                if (isRight(parsedEID)) {
                    expect(parsedEID.right.equals(validEID)).toBeTruthy();
                }
            })
        );
    });

    test('invalid EID can never be parsed', () => {
        fc.assert(
            fc.property(eidGenerator, mutantGenerator, (validEID, mutator) => {
                const mutated = mutator.mutate(validEID);
                const parsed = EID.parse(mutated);
                expect(isLeft(parsed)).toBeTruthy();
            })
        );
    });
});

class Mutator {
    constructor(public name: string, private func: (eid: EID) => fc.Arbitrary<string>) {
    }

    mutate(eid: EID): string {
        return sample(this.func(eid))[0];
    }
}

const aMutator: Mutator = new Mutator('a mutator', (eid: EID) =>
    fc.constant('Implement this first mutator')
);

const mutantGenerator: fc.Arbitrary<Mutator> = fc.constant(aMutator);