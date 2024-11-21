import { convertIv, convertKey, loadFile } from "./utils";
import { Encryption } from "../src/encryption";
import * as fc from "fast-check";

describe("Encryption", () => {
  let encryption: Encryption;

  beforeAll(() => {
    const key = convertKey("Advent Of Craft");
    const iv = convertIv("2024");
    encryption = new Encryption(key, iv);
  });

  test("should encrypt a known string and match the expected Base64 result", () => {
    expect(
      encryption.encrypt(
        "Unlock Your Potential with the Advent Of Craft Calendar!"
      )
    ).toBe(
      "L7wht/YddOoTvYvrc+wFcZhtXNvZ2cHFxq9ND27h1Ovv/aWLxN8lWv1xMsguM/R4Yodk3rn9cppI+YarggtPjA=="
    );
  });

  // It is a Property-Based test that checks the below property
  // I'm pretty sure we will talk about this concept during our Journey 🎅
  test("for all x (x: valid string) -> decrypt(encrypt(x)) == x", () => {
    fc.assert(
      fc.property(fc.string(), (originalText: string) => {
        return (
          encryption.decrypt(encryption.encrypt(originalText)) === originalText
        );
      })
    );
  });

  test("should decrypt email", () => {
    const email = loadFile("EncryptedEmail.txt");
    expect(encryption.decrypt(email)).toBe(
      "Dear consultant,\n\nWe are facing an unprecedented challenge in Christmas Town.\n\nThe systems that keep our magical operations running smoothly are outdated, fragile, and in dire need of modernization. \nWe urgently require your expertise to ensure Christmas happens this year.\nOur town is located within a mountain circlet at the North Pole, surrounded by high peaks and protected by an advanced communication and shield system to hide it from the outside world.\n\nYou have been selected for your exceptional skills and dedication. \nPlease report to the North Pole immediately. \n\nEnclosed are your travel details and a non-disclosure agreement that you must sign upon arrival.\nOur dwarf friends from the security will receive and escort you in as soon as you check security.\nIn the following days, you will receive bracelets to be able to pass through the magic shield.\n\nTime is of the essence.\nYou must arrive before the beginning of December to be able to acclimate yourself with all the systems.\n\nWe are counting on you to help save Christmas.\n\nSincerely,\n\nSanta Claus 🎅"
    );
  });
});
