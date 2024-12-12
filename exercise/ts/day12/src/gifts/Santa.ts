import { Child } from './Child';
import { Toy } from './Toy';

export class Santa {
    private readonly childrenRepository: Child[] = [];

    addChild(child: Child): void {
        this.childrenRepository.push(child);
    }

    chooseToyForChild(childName: string): Toy | undefined {
        const foundChild = this.childrenRepository.find(child => child.name === childName);

        if (!foundChild) {
            throw new Error('No such child found');
        }

        if (foundChild.behavior === 'naughty') {
            return foundChild.wishlist[2];
        } else if (foundChild.behavior === 'nice') {
            return foundChild.wishlist[1];
        } else if (foundChild.behavior === 'very nice') {
            return foundChild.wishlist[0];
        }

        return undefined;
    }
}