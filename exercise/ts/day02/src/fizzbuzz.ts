import { second } from "fp-ts/lib/Reader";
import { none, Option, some } from "fp-ts/Option";

export const min = 1;
export const max = 1155;

type FizzBuzzMapping = Map<number, string>;

let defaultMapping: FizzBuzzMapping = new Map([
  [11, "Bang"],
  [7, "Whizz"],
  [5, "Buzz"],
  [3, "Fizz"],
]);

export const fizzbuzz =
  (conf: FizzBuzzMapping = defaultMapping) =>
  (input: number): Option<string> =>
    isOutOfRange(input) ? none : some(convertSafely(input, conf));

function convertSafely(input: number, conf: FizzBuzzMapping): string {
  const fullMap = createFizzBuzzMap(conf);
  for (const [divisor, value] of fullMap) {
    if (is(divisor, input)) {
      return value;
    }
  }
  return input.toString();
}

export const createFizzBuzzMap = (
  minimalMap: FizzBuzzMapping
): FizzBuzzMapping => {
  const fullMap = new Map();

  function combine(
    minimalMap,
    currentKey = 1,
    currentValue = "",
    startIndex = 0
  ) {
    const keys = [...minimalMap.keys()];
    const values = [...minimalMap.values()];
    if (currentKey > 1) {
      fullMap.set(currentKey, currentValue);
    }

    // Generate further combinations
    for (let i = startIndex; i < keys.length; i++) {
      combine(
        minimalMap,
        currentKey * keys[i],
        currentValue + values[i],
        i + 1
      );
    }
  }

  combine(new Map([...minimalMap.entries()].sort(ascendingOrder)));
  return new Map([...fullMap.entries()].sort(descendingOrder));
};

const ascendingOrder = ([a]: [number, string], [b]: [number, string]) => a - b;
const descendingOrder = ([a]: [number, string], [b]: [number, string]) => b - a;

const is = (divisor: number, input: number): boolean => input % divisor === 0;

const isOutOfRange = (input: number): boolean => input < min || input > max;
