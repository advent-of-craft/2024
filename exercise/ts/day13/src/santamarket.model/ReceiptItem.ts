import { Product } from './Product';

export class ReceiptItem {
    constructor(
        public product: Product,
        public quantity: number,
        public price: number,
        public totalPrice: number
    ) {}

    equals(other: ReceiptItem): boolean {
        return (
            other instanceof ReceiptItem &&
            this.product.equals(other.product) &&
            Math.abs(this.price - other.price) < 0.001 &&
            Math.abs(this.totalPrice - other.totalPrice) < 0.001 &&
            Math.abs(this.quantity - other.quantity) < 0.001
        );
    }
}
