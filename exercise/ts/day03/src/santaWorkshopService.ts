import { Gift } from "./gift";

type GiftConfig = {
  giftName: string;
  weight: number;
  color: string;
  material: string;
};

export class SantaWorkshopService {
  private readonly preparedGifts: Gift[] = [];

  public prepareGift(giftConfig: GiftConfig): Gift {
    const { giftName, weight, color, material } = giftConfig;
    if (weight > 5) {
      throw new Error("Gift is too heavy for Santa's sleigh");
    }
    if (!giftName || !color || !material) {
      throw new Error("Gift is missing required parameters");
    }

    const gift = new Gift(giftName, weight, color, material);
    this.preparedGifts.push(gift);

    return gift;
  }
}
