import * as E from "fp-ts/Either";
import {Either} from "fp-ts/lib/Either";

export const rightValue = <L, R>(either: Either<L, R>): R => {
    if (E.isRight(either)) {
        return either.right;
    }
    throw new Error('Not a Right value');
};

export const leftValue = <L, R>(either: Either<L, R>): L => {
    if (E.isLeft(either)) {
        return either.left;
    }
    throw new Error('Not a Left value');
};