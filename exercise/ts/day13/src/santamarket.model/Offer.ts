import {SpecialOfferType} from "./SpecialOfferType";
import {Product} from "./Product";

export class Offer {
    constructor(public offerType: SpecialOfferType, public product: Product, public argument: number) {}
}