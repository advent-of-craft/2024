import {SantaCommunicator} from "../src/santaCommunicator";
import {TestLogger} from "./doubles/testLogger";

const SantaCommunicatorSpec = 'Dasher';
const NORTH_POLE = 'North Pole';
const numberOfDaysToRest = 2;
const numberOfDayBeforeChristmas = 24;

describe('SantaCommunicator', () => {
    let communicator: SantaCommunicator;
    let logger: TestLogger;

    beforeEach(() => {
        communicator = new SantaCommunicator(numberOfDaysToRest);
        logger = new TestLogger();
    });

    test('composeMessage', () => {
        const message = communicator.composeMessage(SantaCommunicatorSpec, NORTH_POLE, 5, numberOfDayBeforeChristmas);
        expect(message).toEqual('Dear Dasher, please return from North Pole in 17 day(s) to be ready and rest before Christmas.');
    });

    test('shouldDetectOverdueReindeer', () => {
        const overdue = communicator.isOverdue(SantaCommunicatorSpec, NORTH_POLE, numberOfDayBeforeChristmas, numberOfDayBeforeChristmas, logger);

        expect(overdue).toBeTruthy();
        expect(logger.getLog()).toEqual('Overdue for Dasher located North Pole.');
    });

    test('shouldReturnFalseWhenNoOverdue', () => {
        const overdue = communicator.isOverdue(SantaCommunicatorSpec, NORTH_POLE, numberOfDayBeforeChristmas - numberOfDaysToRest - 1, numberOfDayBeforeChristmas, logger);
        expect(overdue).toBeFalsy();
    });
});