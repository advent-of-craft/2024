import {Preparation} from "../src/christmas/preparation";
import {ToyType} from "../src/christmas/toyType";

describe('Preparation', () => {
    test.each([
        [-1, 'No gifts to prepare.'],
        [0, 'No gifts to prepare.'],
        [1, 'Elves will prepare the gifts.'],
        [49, 'Elves will prepare the gifts.'],
        [50, 'Santa will prepare the gifts.']
    ])('prepareGifts should return the correct preparation message for %d gifts', (numberOfGifts, expected) => {
        expect(Preparation.prepareGifts(numberOfGifts)).toBe(expected);
    });

    test.each([
        [1, 'Baby'],
        [3, 'Toddler'],
        [6, 'Child'],
        [13, 'Teen']
    ])('categorizeGift should return the correct category for age %d', (age, expectedCategory) => {
        expect(Preparation.categorizeGift(age)).toBe(expectedCategory);
    });

    test.each([
        [ToyType.EDUCATIONAL, 25, 100, true],
        [ToyType.FUN, 30, 100, true],
        [ToyType.CREATIVE, 20, 100, true],
        [ToyType.EDUCATIONAL, 20, 100, false],
        [ToyType.FUN, 29, 100, false],
        [ToyType.CREATIVE, 15, 100, false]
    ])('ensureToyBalance should return %s for toy type %s with %d toys out of %d total toys', (toyType, toysCount, totalToys, expected) => {
        expect(Preparation.ensureToyBalance(toyType, toysCount, totalToys)).toBe(expected);
    });
});
