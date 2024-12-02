import { fizzbuzz, max, min } from "../src/fizzbuzz";
import * as O from "fp-ts/Option";
import { isNone, isSome } from "fp-ts/Option";
import * as fc from "fast-check";
import { pipe } from "fp-ts/function";

describe("FizzBuzz should return", () => {
  test.each([
    [1, "1"],
    [7, "Whizz"],
    [11, "Bang"],
    [67, "67"],
    [82, "82"],
    [3, "Fizz"],
    [21, "FizzWhizz"],
    [33, "FizzBang"],
    [55, "BuzzBang"],
    [66, "FizzBang"],
    [77, "WhizzBang"],
    [99, "FizzBang"],
    [5, "Buzz"],
    [50, "Buzz"],
    [85, "Buzz"],
    [15, "FizzBuzz"],
    [30, "FizzBuzz"],
    [45, "FizzBuzz"],
    [35, "BuzzWhizz"],
    [70, "BuzzWhizz"],
    [105, "FizzBuzzWhizz"],
    [165, "FizzBuzzBang"],
    [330, "FizzBuzzBang"],
    [1155, "FizzBuzzBangWhizz"],
  ])("its representation %s -> %s", (input, expectedResult) => {
    const conversionResult = fizzbuzz(input);
    expect(isSome(conversionResult)).toBeTruthy();

    if (isSome(conversionResult)) {
      expect(conversionResult.value).toBe(expectedResult);
    }
  });

  test("valid strings for numbers between 1 and 1155", () => {
    fc.assert(
      fc.property(
        fc.integer().filter((n) => n >= min && n <= max),
        (n) => isConvertValid(n)
      )
    );
  });

  const isConvertValid = (input: number): boolean =>
    pipe(
      fizzbuzz(input),
      O.exists((result) => validStringsFor(input).includes(result))
    );

  const validStringsFor = (x: number): string[] => [
    "Fizz",
    "Buzz",
    "Whizz",
    "FizzBuzz",
    "FizzWhizz",
    "Bang",
    "FizzBang",
    "BuzzBang",
    "WhizzBang",
    "BuzzWhizz",
    "FizzBuzzWhizz",
    "FizzBuzzBang",
    "FizzBuzzBangWhizz",

    x.toString(),
  ];

  test("none for numbers out of range", () => {
    fc.assert(
      fc.property(
        fc.integer().filter((n) => n < min || n > max),
        (n) => isNone(fizzbuzz(n))
      )
    );
  });
});
