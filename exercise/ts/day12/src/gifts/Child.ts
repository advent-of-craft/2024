import { Toy } from './Toy';

export class Child {
    public wishlist: Toy[] = [];

    constructor(public name: string, public behavior: string) {}

    setWishlist(firstChoice: Toy, secondChoice: Toy, thirdChoice: Toy): void {
        this.wishlist = [firstChoice, secondChoice, thirdChoice];
    }
}