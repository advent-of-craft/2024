import {
  GiftConfig,
  SantaWorkshopService,
  SLEIGH_WEIGHT_LIMIT,
} from "../src/santaWorkshopService";
import { Gift } from "../src/gift";
import fc from "fast-check";

fc.configureGlobal({ numRuns: 1_000 });

const giftCongig = {
  giftName: "Furby",
  weight: 1,
  color: "Multi",
  material: "Cotton",
};
describe("SantaWorkshopService", () => {
  let service: SantaWorkshopService;

  beforeEach(() => {
    service = new SantaWorkshopService();
  });

  it("should prepare a gift with valid parameters", () => {
    fc.assert(
      fc.property(
        fc.record<GiftConfig>({
          weight: fc.integer({ min: 1, max: 5 }),
          giftName: fc.string({ minLength: 1 }),
          color: fc.string({ minLength: 1 }),
          material: fc.string({ minLength: 1 }),
        }),
        (giftParam) => {
          const gift = service.prepareGift(giftParam);
          expect(giftIsValid(gift, giftParam)).toBe(true);
        }
      )
    );
  });

  it("should throw an error if gift is too heavy", () => {
    fc.assert(
      fc.property(
        fc.integer({ min: 6, max: 10000000 }),
        fc.string(),
        fc.string(),
        fc.string(),
        (weight, giftName, color, material) => {
          expect(() =>
            service.prepareGift({ giftName, weight, color, material })
          ).toThrow("Gift is too heavy for Santa's sleigh");
        }
      )
    );
  });

  it("should add an attribute to a gift", () => {
    const gift = service.prepareGift(giftCongig);

    fc.assert(
      fc.property(
        fc.dictionary<string>(
          fc.string({ minLength: 1, unit: "grapheme" }),
          fc.string({ minLength: 0, unit: "grapheme" })
        ),
        ({ key, value }) => {
          gift.addAttribute(key, value);
          expect(gift.getAttribute(key)).toBe(value);
        }
      )
    );
  });

  it("should add an recommendedAge attribute to a gift", () => {
    const gift = service.prepareGift(giftCongig);

    fc.assert(
      fc.property(fc.integer({ min: 0, max: 99 }), (value) => {
        gift.addAttribute("recommendedAge", value.toString());
        expect(gift.getRecommendedAge()).toBe(value);
      })
    );
  });

  it("should assign a gift to a child", () => {
    const gift = service.prepareGift(giftCongig);

    fc.assert(
      fc.property(fc.string({ unit: "grapheme" }), (childName) => {
        gift.assignToChild(childName);
        expect(gift.getAttribute("assignedTo")).toBe(childName);
      })
    );
  });
});

function giftIsValid(gift: Gift, giftParameters): boolean {
  return (
    giftParameters.weight <= SLEIGH_WEIGHT_LIMIT &&
    gift instanceof Gift &&
    gift.toString() ===
      `A ${giftParameters.color}-colored ${giftParameters.giftName} weighing ${giftParameters.weight} kg made in ${giftParameters.material}`
  );
}
