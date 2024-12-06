import { Routine } from '../src/routine/routine';
import { EmailService } from '../src/routine/emailService';
import { ScheduleService } from '../src/routine/scheduleService';
import { ReindeerFeeder } from '../src/routine/reindeerFeeder';
import { Schedule } from '../src/routine/schedule';
import { EmailServiceDouble } from './doubles/emailService';
import { ScheduleServiceDouble } from './doubles/scheduleService';
import { ReindeerFeederDouble } from './doubles/reindeerFeeder';

describe('Routine', () => {
    test('start routine with Jest', () => {
        const schedule = new Schedule()
        const emailService: EmailService = {
            readNewEmails: jest.fn()
        }
        const scheduleService: ScheduleService = {
            todaySchedule: () => schedule,
            continueDay: jest.fn(),
            organizeMyDay: jest.fn()
        }
        const reindeerFeeder: ReindeerFeeder = {
            feedReindeers: jest.fn()
        }
        const underTest = new Routine(emailService,scheduleService, reindeerFeeder);

        underTest.start();

        expect(scheduleService.organizeMyDay).toHaveBeenCalledWith(schedule)
        expect(reindeerFeeder.feedReindeers).toHaveBeenCalled();
        expect(emailService.readNewEmails).toHaveBeenCalled();
        expect(scheduleService.continueDay).toHaveBeenCalled();
    });

    test('start routine with manual test doubles', () => {
        const emailService = new EmailServiceDouble();
        const scheduleService = new ScheduleServiceDouble();
        const reindeerFeeder = new ReindeerFeederDouble();
        const underTest = new Routine(emailService,scheduleService, reindeerFeeder);

        underTest.start();

        expect(scheduleService.organizeMyDayCalled).toBe(true)
        expect(reindeerFeeder.feedReindeersCalled).toBe(true)
        expect(emailService.readNewEmailsCalled).toBe(true);
        expect(scheduleService.continueDayCalled).toBe(true);
    });
});
