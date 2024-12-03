import {SantaWorkshopService} from "../src/santaWorkshopService";
import {Gift} from "../src/gift";
import fc from "fast-check";

const UnderweightGiftArbitrary = fc.float().filter((weight) => weight <= 5);
const OverweightGiftArbitrary = fc.float().filter((weight) => weight > 5);
const ValidAgeAttributeArbitrary = fc.integer().filter(age => age > 0);

describe('SantaWorkshopService', () => {
    let service: SantaWorkshopService;

    beforeEach(() => {
        service = new SantaWorkshopService();
    });

    it('should prepare a gift with valid parameters', () => {
        const giftName = 'Bitzee';
        const color = 'Purple';
        const material = 'Plastic';

        fc.assert(fc.property(UnderweightGiftArbitrary, (weight) => {
            const gift = service.prepareGift(giftName, weight, color, material);

            expect(gift).toBeInstanceOf(Gift);
        }))
    });

    it('should throw an error if gift is too heavy', () => {
        const giftName = 'Dog-E';
        const color = 'White';
        const material = 'Metal';
        fc.assert(fc.property(OverweightGiftArbitrary, (weight) => {
            expect(() => service.prepareGift(giftName, weight, color, material)).toThrow('Gift is too heavy for Santa\'s sleigh');
        }))
    });

    it('should add an recommended age to a gift', () => {
        const giftName = 'Furby';
        const weight = 1;
        const color = 'Multi';
        const material = 'Cotton';

        fc.assert(fc.property(ValidAgeAttributeArbitrary, (age) => {
            const gift = service.prepareGift(giftName, weight, color, material);
            gift.addAttribute('recommendedAge', `${age}`);

            expect(gift.getRecommendedAge()).toBe(age);
        }))
    });
});
