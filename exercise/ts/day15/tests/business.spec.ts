import {Factory, Inventory, WishList} from "../src/dependencies";
import {Child, Gift, ManufacturedGift} from "../src/models";
import {Business} from "../src/business";

describe('Business Logic', () => {
    let factory: Factory;
    let inventory: Inventory;
    let wishList: WishList;
    let john: Child;
    let toy: Gift;
    let manufacturedGift: ManufacturedGift;

    beforeEach(() => {
        factory = new Factory();
        inventory = new Inventory();
        wishList = new WishList();

        john = new Child('John');
        toy = new Gift('Toy');
        manufacturedGift = new ManufacturedGift('123');
    });

    test('Gift should be loaded into Sleigh', () => {
        wishList.set(john, toy);
        factory.set(toy, manufacturedGift);
        inventory.set('123', toy);

        const business = new Business(factory, inventory, wishList);
        const sleigh = business.loadGiftsInSleigh(john);

        expect(sleigh.get(john)).toBe('Gift: Toy has been loaded!');
    });

    test('Gift should not be loaded if child is not on the wish list', () => {
        const business = new Business(factory, inventory, wishList);
        const sleigh = business.loadGiftsInSleigh(john);

        expect(sleigh.has(john)).toBe(false);
    });

    test('Gift should not be loaded if the toy was not manufactured', () => {
        wishList.set(john, toy);

        const business = new Business(factory, inventory, wishList);
        const sleigh = business.loadGiftsInSleigh(john);

        expect(sleigh.has(john)).toBe(false);
    });

    test('Gift should not be loaded if the toy was misplaced', () => {
        wishList.set(john, toy);
        factory.set(toy, manufacturedGift);

        const business = new Business(factory, inventory, wishList);
        const sleigh = business.loadGiftsInSleigh(john);

        expect(sleigh.has(john)).toBe(false);
    });
});
