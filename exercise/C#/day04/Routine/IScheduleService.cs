namespace Routine
{
    public interface IScheduleService
    {
        Schedule TodaySchedule();
        void OrganizeMyDay(Schedule schedule);
        void Continue();
    }
}