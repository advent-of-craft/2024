import {SantaWorkshopService} from "../src/santaWorkshopService";
import {Gift} from "../src/gift";

describe('SantaWorkshopService', () => {
    let service: SantaWorkshopService;

    beforeEach(() => {
        service = new SantaWorkshopService();
    });

    it('should prepare a gift with valid parameters', () => {
        const giftName = 'Bitzee';
        const weight = 3;
        const color = 'Purple';
        const material = 'Plastic';

        const gift = service.prepareGift(giftName, weight, color, material);

        expect(gift).toBeInstanceOf(Gift);
    });

    it('should throw an error if gift is too heavy', () => {
        const giftName = 'Dog-E';
        const weight = 6;
        const color = 'White';
        const material = 'Metal';

        expect(() => service.prepareGift(giftName, weight, color, material)).toThrow('Gift is too heavy for Santa\'s sleigh');
    });

    it('should add an attribute to a gift', () => {
        const giftName = 'Furby';
        const weight = 1;
        const color = 'Multi';
        const material = 'Cotton';

        const gift = service.prepareGift(giftName, weight, color, material);
        gift.addAttribute('recommendedAge', '3');

        expect(gift.getRecommendedAge()).toBe(3);
    });
});