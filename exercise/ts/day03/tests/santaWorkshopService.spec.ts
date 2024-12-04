import {SantaWorkshopService} from "../src/santaWorkshopService";
import {Gift} from "../src/gift";
import { faker } from '@faker-js/faker';

describe('SantaWorkshopService', () => {
    let service: SantaWorkshopService;

    beforeEach(() => {
        service = new SantaWorkshopService();
    });

    const validWeight = () => faker.number.float({min: 0, max: 5, fractionDigits: 2});
    const invalidWeight = () => faker.number.float({min: 6, max: Number.MAX_SAFE_INTEGER, fractionDigits: 2})
    const prepareGiftFor = (weight: number) => {
        const giftName = faker.commerce.productName();
        const color = faker.color.human()
        const material = faker.commerce.productMaterial();

        return service.prepareGift(giftName, weight, color, material);
    }

    it('should prepare a gift with valid parameters', () => {
        const gift = prepareGiftFor(validWeight())
        expect(gift).toBeInstanceOf(Gift);
    });

    it('should throw an error if gift is too heavy', () => {
        expect(() => prepareGiftFor(invalidWeight())).toThrow('Gift is too heavy for Santa\'s sleigh');
    });

    it('should add an attribute to a gift', () => {
        const gift = prepareGiftFor(validWeight())
        gift.addAttribute('recommendedAge', '3');

        expect(gift.getRecommendedAge()).toBe(3);
    });
});
