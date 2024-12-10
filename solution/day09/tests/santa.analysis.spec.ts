import {SantaService} from "../src/santaService";
import {ChildBuilder} from "./childBuilder";

describe('santa analyzing child requests', () => {
    const service = new SantaService();

    test('request is approved for nice child with feasible gift', () => {
        expect(evaluateRequestFor(
            child => child.nice())
        ).toBeTruthy();
    });

    test('request is denied for naughty child', () => {
        expect(evaluateRequestFor(
            child => child.naughty())
        ).toBeFalsy();
    });

    test('request is denied for nice child with infeasible gift', () => {
        expect(evaluateRequestFor(
            child => child
                .nice()
                .requestingInfeasibleGift())
        ).toBeFalsy();
    });

    type ChildConfiguration = (builder: ChildBuilder) => ChildBuilder;

    function evaluateRequestFor(childConfiguration: ChildConfiguration) {
        return service.evaluateRequest(
            childConfiguration(ChildBuilder.aChild())
                .build()
        );
    }
});