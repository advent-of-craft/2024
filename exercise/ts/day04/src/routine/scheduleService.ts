import {Schedule} from "./schedule";

export interface ScheduleService {
    todaySchedule(): Schedule;

    organizeMyDay(schedule: Schedule): void;

    continueDay(): void;
}