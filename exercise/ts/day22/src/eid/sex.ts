import {Either, left, right} from 'fp-ts/Either';
import {ParsingException} from "./parsingException";

export enum Sex {
    Sloubi = '1',
    Gagna = '2',
    Catact = '3'
}

export namespace Sex {
    export function parse(potentialSex: string): Either<ParsingException, Sex> {
        switch (potentialSex) {
            case '1':
                return right(Sex.Sloubi);
            case '2':
                return right(Sex.Gagna);
            case '3':
                return right(Sex.Catact);
            default:
                return left(new ParsingException('Not a valid sex'));
        }
    }
}