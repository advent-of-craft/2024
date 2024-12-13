import { Product } from './Product';

export interface SantamarketCatalog {
    addProduct(product: Product, price: number): void;
    getUnitPrice(product: Product): number;
}