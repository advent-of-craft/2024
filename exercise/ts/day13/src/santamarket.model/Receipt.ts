import { ReceiptItem } from './ReceiptItem';
import { Discount } from './Discount';
import {Product} from "./Product";

export class Receipt {
    private items: ReceiptItem[] = [];
    private discounts: Discount[] = [];

    addProduct(product: Product, quantity: number, price: number, totalPrice: number) {
        this.items.push(new ReceiptItem(product, quantity, price, totalPrice));
    }

    addDiscount(discount: Discount) {
        this.discounts.push(discount);
    }

    getTotalPrice(): number {
        return (
            this.items.reduce((sum, item) => sum + item.totalPrice, 0) +
            this.discounts.reduce((sum, discount) => sum + discount.discountAmount, 0)
        );
    }

    getItems(): ReadonlyArray<ReceiptItem> {
        return this.items;
    }

    getDiscounts(): ReadonlyArray<Discount> {
        return this.discounts;
    }
}
