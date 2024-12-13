import { Product } from './Product';
import { Offer } from './Offer';
import { SpecialOfferType } from './SpecialOfferType';
import { Receipt } from './Receipt';
import { SantamarketCatalog } from './SantamarketCatalog';
import { Discount } from './Discount';

export class ShoppingSleigh {
    private items: { product: Product; quantity: number }[] = [];
    private productQuantities: Map<Product, number> = new Map();

    getItems(): { product: Product; quantity: number }[] {
        return [...this.items];
    }

    addItem(product: Product): void {
        this.addItemQuantity(product, 1.0);
    }

    addItemQuantity(product: Product, quantity: number): void {
        this.items.push({ product, quantity });
        const currentQuantity = this.productQuantities.get(product) || 0;
        this.productQuantities.set(product, currentQuantity + quantity);
    }

    handleOffers(receipt: Receipt, offers: Map<Product, Offer>, catalog: SantamarketCatalog): void {
        this.productQuantities.forEach((quantity, product) => {
            if (offers.has(product)) {
                const offer = offers.get(product)!;
                const unitPrice = catalog.getUnitPrice(product);
                const quantityAsInt = Math.floor(quantity);
                let discount: Discount | null = null;
                let x = 1;

                if (offer.offerType === SpecialOfferType.THREE_FOR_TWO) {
                    x = 3;
                    const numberOfXs = Math.floor(quantityAsInt / x);
                    if (quantityAsInt >= 3) {
                        const discountAmount = quantity * unitPrice - (numberOfXs * 2 * unitPrice + (quantityAsInt % 3) * unitPrice);
                        discount = new Discount(product, '3 for 2', -discountAmount);
                    }
                }

                if (offer.offerType === SpecialOfferType.TWO_FOR_AMOUNT) {
                    x = 2;
                    if (quantityAsInt >= 2) {
                        const total = offer.argument * Math.floor(quantityAsInt / x) + (quantityAsInt % 2) * unitPrice;
                        const discountN = unitPrice * quantity - total;
                        discount = new Discount(product, `2 for ${offer.argument}`, -discountN);
                    }
                }

                if (offer.offerType === SpecialOfferType.FIVE_FOR_AMOUNT) {
                    x = 5;
                    const numberOfXs = Math.floor(quantityAsInt / x);
                    if (quantityAsInt >= 5) {
                        const discountTotal = unitPrice * quantity - (offer.argument * numberOfXs + (quantityAsInt % 5) * unitPrice);
                        discount = new Discount(product, `5 for ${offer.argument}`, -discountTotal);
                    }
                }

                if (offer.offerType === SpecialOfferType.TEN_PERCENT_DISCOUNT) {
                    const discountAmount = -quantity * unitPrice * (offer.argument / 100);
                    discount = new Discount(product, `${offer.argument}% off`, discountAmount);
                }

                if (discount) {
                    receipt.addDiscount(discount);
                }
            }
        });
    }
}
