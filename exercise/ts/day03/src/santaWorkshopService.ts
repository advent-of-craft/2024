import { Gift } from "./gift";

export type GiftConfig = {
  giftName: string;
  weight: number;
  color: string;
  material: string;
};

export const SLEIGH_WEIGHT_LIMIT = 5;
export class SantaWorkshopService {
  private readonly preparedGifts: Gift[] = [];

  public prepareGift(giftConfig: GiftConfig): Gift {
    const { giftName, weight, color, material } = giftConfig;
    if (weight > SLEIGH_WEIGHT_LIMIT) {
      throw new Error("Gift is too heavy for Santa's sleigh");
    }
    if (!giftName) {
      throw new Error("Gift is missing the required parameter: giftName");
    }
    if (!color) {
      throw new Error("Gift is missing the required parameter: color");
    }
    if (!material) {
      throw new Error("Gift is missing the required parameter: material");
    }

    const gift = new Gift(giftName, weight, color, material);
    this.preparedGifts.push(gift);

    return gift;
  }
}
