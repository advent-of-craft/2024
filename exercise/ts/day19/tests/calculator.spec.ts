import {OverflowException, SantaTravelCalculator} from "../src/travel/santaTravelCalculator";

describe("SantaTravelCalculator", () => {
    test.each([
        [1, 1],
        [2, 3],
        [5, 31],
        [10, 1023],
        [20, 1048575],
        [30, 1073741823]
    ])("should calculate the distance correctly for %d reindeers", (numberOfReindeers, expectedDistance) => {
        expect(SantaTravelCalculator.calculateTotalDistanceRecursively(numberOfReindeers))
            .toBe(expectedDistance);
    });

    test.each([
        [32],
        [50]
    ])("should fail for numbers greater or equal to 32 (%d)", (numberOfReindeers) => {
        expect(() => SantaTravelCalculator.calculateTotalDistanceRecursively(numberOfReindeers))
            .toThrow(OverflowException);
    });
});