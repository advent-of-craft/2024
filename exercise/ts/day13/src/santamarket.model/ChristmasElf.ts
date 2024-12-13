import { SantamarketCatalog } from './SantamarketCatalog';
import { Product } from './Product';
import { Offer } from './Offer';
import { SpecialOfferType } from './SpecialOfferType';
import { ShoppingSleigh } from './ShoppingSleigh';
import { Receipt } from './Receipt';

export class ChristmasElf {
    private readonly catalog: SantamarketCatalog;
    private readonly offers: Map<Product, Offer>;

    constructor(catalog: SantamarketCatalog) {
        this.catalog = catalog;
        this.offers = new Map<Product, Offer>();
    }

    addSpecialOffer(offerType: SpecialOfferType, product: Product, argument: number): void {
        this.offers.set(product, new Offer(offerType, product, argument));
    }

    checksOutArticlesFrom(sleigh: ShoppingSleigh): Receipt {
        const receipt = new Receipt();
        const productQuantities = sleigh.getItems();

        productQuantities.forEach(({ product, quantity }) => {
            const unitPrice = this.catalog.getUnitPrice(product);
            const totalPrice = quantity * unitPrice;
            receipt.addProduct(product, quantity, unitPrice, totalPrice);
        });

        sleigh.handleOffers(receipt, this.offers, this.catalog);

        return receipt;
    }
}
