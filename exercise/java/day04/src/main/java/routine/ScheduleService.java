package routine;

public interface ScheduleService {
    Schedule todaySchedule();

    void organizeMyDay(Schedule schedule);

    void continueDay();
}
