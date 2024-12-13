import {SantamarketCatalog} from "../src/santamarket.model/SantamarketCatalog";
import {Product} from "../src/santamarket.model/Product";

export class FakeCatalog implements SantamarketCatalog {
    private prices: Map<string, number> = new Map();

    addProduct(product: Product, price: number): void {
        this.prices.set(product.name, price);
    }

    getUnitPrice(product: Product): number {
        return this.prices.get(product.name) || 0;
    }
}