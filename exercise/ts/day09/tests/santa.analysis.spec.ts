import {SantaService} from "../src/santaService";
import {Child} from "../src/child";
import {GiftRequest} from "../src/giftRequest";

describe('santa analyzing child requests', () => {
    const service = new SantaService();

    test('request is approved for nice child with feasible gift', () => {
        const niceChild = new Child("Alice", "Thomas", 9, "nice", new GiftRequest("Bicycle", true, "nice to have"));
        expect(service.evaluateRequest(niceChild)).toBeTruthy();
    });

    test('request is denied for naughty child', () => {
        const naughtyChild = new Child("Noa", "Thierry", 6, "naughty", new GiftRequest("SomeToy", true, "dream"));
        expect(service.evaluateRequest(naughtyChild)).toBeFalsy();
    });

    test('request is denied for nice child with infeasible gift', () => {
        const niceChildWithInfeasibleGift = new Child("Charlie", "Joie", 3, "nice", new GiftRequest("AnotherToy", false, "dream"));
        expect(service.evaluateRequest(niceChildWithInfeasibleGift)).toBeFalsy();
    });
});