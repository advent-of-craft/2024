import { Product } from './Product';

export class Discount {
    constructor(public product: Product, public description: string, public discountAmount: number) {}

    equals(other: Discount): boolean {
        return (
            other instanceof Discount &&
            this.product.equals(other.product) &&
            this.description === other.description &&
            Math.abs(this.discountAmount - other.discountAmount) < 0.001
        );
    }
}
