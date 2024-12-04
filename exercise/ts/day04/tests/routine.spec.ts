import { Routine } from "../src/routine/routine";
import { Schedule } from "../src/routine/schedule";
import {
  EmailServiceDouble,
  ReindeerFeederDouble,
  ScheduleServiceDouble,
} from "./double";

const emailServiceMock = {
  readNewEmails: jest.fn(),
};
const schedule = new Schedule();
const scheduleServiceMock = {
  organizeMyDay: jest.fn(),
  todaySchedule: jest.fn(() => schedule),
  continueDay: jest.fn(),
};
const reindeerFeederMock = {
  feedReindeers: jest.fn(),
};

describe("Routine", () => {
  test("should start routine and call all services using Jest mocks", () => {
    const routine = new Routine(
      emailServiceMock,
      scheduleServiceMock,
      reindeerFeederMock
    );
    routine.start();
    expect(scheduleServiceMock.todaySchedule).toHaveBeenCalled();
    expect(scheduleServiceMock.organizeMyDay).toHaveBeenCalledWith(schedule);
    expect(reindeerFeederMock.feedReindeers).toHaveBeenCalled();
    expect(emailServiceMock.readNewEmails).toHaveBeenCalled();
    expect(scheduleServiceMock.continueDay).toHaveBeenCalled();
  });

  test("should start routine and call all services using manual test doubles", () => {
    const emailService = new EmailServiceDouble();
    const scheduleService = new ScheduleServiceDouble();
    const reindeerFeeder = new ReindeerFeederDouble();
    const routine = new Routine(emailService, scheduleService, reindeerFeeder);
    routine.start();
    expect(reindeerFeeder.isFeedingDone()).toBeTruthy();
    expect(emailService.isNewMailRead()).toBeTruthy();
    expect(scheduleService.myDayIsOrganized()).toBeTruthy();
    expect(scheduleService.continueDayWasCalled()).toBeTruthy();
  });
});
