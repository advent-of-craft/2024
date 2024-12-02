import {Logger} from "./logger";

type Reindeer = {
    reindeerName: string;
    currentLocation: string;
    numbersOfDaysForComingBack: number;
}

export class SantaCommunicator {
    constructor(private readonly config: { numberOfDaysToRest: number; numberOfDaysBeforeChristmas: number; }) {
    }

    public composeMessage({reindeerName, currentLocation, numbersOfDaysForComingBack}: Reindeer): string {
        const daysBeforeReturn = this.daysBeforeReturn(numbersOfDaysForComingBack);
        return `Dear ${reindeerName}, please return from ${currentLocation} in ${daysBeforeReturn} day(s) to be ready and rest before Christmas.`;
    }

    public isOverdue({reindeerName, currentLocation, numbersOfDaysForComingBack,}: Reindeer, logger: Logger): boolean {
        if (this.daysBeforeReturn(numbersOfDaysForComingBack) <= 0) {
            logger.log(`Overdue for ${reindeerName} located ${currentLocation}.`);
            return true;
        }
        return false;
    }

    private daysBeforeReturn(numbersOfDaysForComingBack: number): number {
        return this.config.numberOfDaysBeforeChristmas - numbersOfDaysForComingBack - this.config.numberOfDaysToRest;
    }
}
