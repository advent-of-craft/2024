import { Schedule } from "../src/routine/schedule";

export class EmailServiceDouble {
  private _readNewEmailsWasCalled: boolean = false;
  readNewEmails(): void {
    this._readNewEmailsWasCalled = true;
  }
  isNewMailRead(): boolean {
    return this._readNewEmailsWasCalled;
  }
}

export class ReindeerFeederDouble {
  private _feedReindeersWasCalled: boolean = false;
  feedReindeers(): void {
    this._feedReindeersWasCalled = true;
  }
  isFeedingDone(): boolean {
    return this._feedReindeersWasCalled;
  }
}

export class ScheduleServiceDouble {
  private _organizeMyDayWasCalledWithSchedule: boolean = false;
  private _continueDayWasCalled: boolean = false;
  private readonly _schedule: Schedule = new Schedule();

  organizeMyDay(schedule: Schedule): void {
    this._organizeMyDayWasCalledWithSchedule = true;
  }
  myDayIsOrganized(): boolean {
    return this._organizeMyDayWasCalledWithSchedule;
  }
  continueDay(): void {
    this._continueDayWasCalled = true;
  }
  continueDayWasCalled(): boolean {
    return this._continueDayWasCalled;
  }
  todaySchedule(): Schedule {
    return this._schedule;
  }
}

export const DUMMY_SCHEDULE = {
  tasks: [],
  events: [],
};
