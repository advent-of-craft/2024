import {none, Option, some} from "fp-ts/Option";

export const min = 1;
export const max = 100;

let mapping: Map<number, string> = new Map([
    [15, 'FizzBuzz'],
    [3, 'Fizz'],
    [5, 'Buzz'],
]);

export const fizzbuzz = (input: number): Option<string> =>
    isOutOfRange(input)
        ? none
        : some(convertSafely(input));

function convertSafely(input: number): string {
    for (const [divisor, value] of mapping) {
        if (is(divisor, input)) {
            return value;
        }
    }
    return input.toString();
}

const is = (divisor: number, input: number): boolean => input % divisor === 0;
const isOutOfRange = (input: number): boolean => input < min || input > max;