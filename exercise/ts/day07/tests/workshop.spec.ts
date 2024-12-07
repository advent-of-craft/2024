import {Gift} from "../src/workshop/gift";
import {Workshop} from "../src/workshop/workshop";
import {Status} from "../src/workshop/status";

describe('Workshop', () => {
    const TOY_NAME = '1 Super Nintendo';

    it('completes a gift and sets its status to produced', () => {
        const workshop = new Workshop();
        workshop.addGift(new Gift(TOY_NAME));

        const completedGift = workshop.completeGift(TOY_NAME);

        expect(completedGift).not.toBeNull();
        expect(completedGift!.getStatus()).toBe(Status.Produced);
    });

    it('returns null when trying to complete a non-existing gift', () => {
        const workshop = new Workshop();
        const completedGift = workshop.completeGift('NonExistingToy');

        expect(completedGift).toBeNull();
    });
});
