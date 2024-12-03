import {fizzbuzz} from '../src/fizzbuzz';
import * as O from 'fp-ts/Option';
import {isNone, isSome} from 'fp-ts/Option';
import * as fc from 'fast-check';
import {pipe} from 'fp-ts/function';

describe('FizzBuzz should return', () => {
    const config = {
        min: 1,
        max: 100,
        mapping: new Map<number, string>([
            [3, 'Fizz'],
            [5, 'Buzz'],
            [7, 'Whizz'],
            [11, 'Bang'],
        ])
    }

    test.each([
        [1, '1'],
        [67, '67'],
        [82, '82'],
        [3, 'Fizz'],
        [66, 'FizzBang'],
        [99, 'FizzBang'],
        [5, 'Buzz'],
        [50, 'Buzz'],
        [85, 'Buzz'],
        [15, 'FizzBuzz'],
        [30, 'FizzBuzz'],
        [45, 'FizzBuzz'],
        [11, 'Bang'],
        [22, 'Bang'],
        [33, 'FizzBang'],
        [44, 'Bang'],
        [55, 'BuzzBang'],
        [77, 'WhizzBang'],
        [88, 'Bang'],
        [7, 'Whizz'],
        [14, 'Whizz'],
        [21, 'FizzWhizz'],
        [28, 'Whizz'],
        [35, 'BuzzWhizz'],
        [42, 'FizzWhizz'],
        [49, 'Whizz'],
        [56, 'Whizz'],
        [63, 'FizzWhizz'],
        [70, 'BuzzWhizz'],
        [84, 'FizzWhizz'],
        [91, 'Whizz'],
        [98, 'Whizz'],
    ])('its representation %s -> %s', (input, expectedResult) => {
        const conversionResult = fizzbuzz(config)(input);
        expect(isSome(conversionResult)).toBeTruthy();

        if (isSome(conversionResult)) {
            expect(conversionResult.value).toBe(expectedResult);
        }
    });

    test('valid strings for numbers between 1 and 100', () => {
        fc.assert(
            fc.property(
                fc.integer().filter(n => n >= config.min && n <= config.max),
                (n) => isConvertValid(n)
            )
        );
    });

    const isConvertValid = (input: number): boolean => pipe(
        fizzbuzz(config)(input),
        O.exists(result => validStringsFor(input).includes(result))
    );

    const validStringsFor = (x: number): string[] => ['Fizz', 'Buzz', 'FizzBuzz', 'Bang', 'FizzBang', 'Whizz', 'FizzWhizz', x.toString()];

    test('none for numbers out of range', () => {
        fc.assert(
            fc.property(
                fc.integer().filter(n => n < config.min || n > config.max),
                (n) => isNone(fizzbuzz(config)(n))
            )
        );
    });
});
