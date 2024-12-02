import {SantaCommunicator} from "../src/santaCommunicator";
import {TestLogger} from "./doubles/testLogger";

const REINDEER_NAME = 'Dasher';
const LOCATION = 'North Pole';
const numberOfDaysToRest = 2;
const numberOfDayBeforeChristmas = 24;

describe('SantaCommunicator', () => {
    let communicator: SantaCommunicator;
    let logger: TestLogger;

    beforeEach(() => {
        communicator = new SantaCommunicator({ numberOfDaysToRest: numberOfDaysToRest, numberOfDaysBeforeChristmas: 24 });
        logger = new TestLogger();
    });

    test('composeMessage', () => {
        const message = communicator.composeMessage({reindeerName : REINDEER_NAME, currentLocation : LOCATION, numbersOfDaysForComingBack : 5});
        expect(message).toEqual('Dear Dasher, please return from North Pole in 17 day(s) to be ready and rest before Christmas.');
    });

    test('shouldDetectOverdueReindeer', () => {
        const overdue = communicator.isOverdue({
            reindeerName: REINDEER_NAME,
            currentLocation: LOCATION,
            numbersOfDaysForComingBack: numberOfDayBeforeChristmas,
        }, logger);

        expect(overdue).toBeTruthy();
        expect(logger.getLog()).toEqual('Overdue for Dasher located North Pole.');
    });

    test('shouldReturnFalseWhenNoOverdue', () => {
        const overdue = communicator.isOverdue({
            reindeerName: REINDEER_NAME,
            currentLocation: LOCATION,
            numbersOfDaysForComingBack: numberOfDayBeforeChristmas - numberOfDaysToRest - 1,
        }, logger);
        expect(overdue).toBeFalsy();
    });
});
