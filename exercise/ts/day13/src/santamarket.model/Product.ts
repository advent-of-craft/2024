import {ProductUnit} from "./ProductUnit";

export class Product {
    constructor(public name: string, public unit: ProductUnit) {}

    equals(other: Product): boolean {
        return other instanceof Product && this.name === other.name && this.unit === other.unit;
    }
}