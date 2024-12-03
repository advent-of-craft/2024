import {none, Option, some} from "fp-ts/Option";

type Config = {
    mapping: Map<number, string>;
    min: number;
    max: number;
}
export const fizzbuzz = ({mapping, min, max}: Config) => (input: number): Option<string> =>
    isOutOfRange({min, max})(input)
        ? none
        : some(convertSafely(mapping)(input));

const convertSafely = (mapping: Map<number, string>) => (input: number): string => {
    const values: string[] = [];
    for (const [divisor, value] of mapping) {
        if (is(divisor, input)) {
            values.push(value);
        }
    }
    return values.length ? values.join('') : input.toString();
};

const is = (divisor: number, input: number): boolean => input % divisor === 0;
const isOutOfRange = ({min, max}: {min:number; max: number;}) => (input: number): boolean => input < min || input > max;
