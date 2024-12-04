import { DUMMY_SCHEDULE, ScheduleServiceDouble } from "./double";

describe("ScheduleService", () => {
  test("should organize my day", () => {
    const scheduleService = new ScheduleServiceDouble();
    scheduleService.organizeMyDay(DUMMY_SCHEDULE);
    expect(scheduleService.myDayIsOrganized()).toBe(true);
  });
});
