namespace Routine
{
    public class Routine(
        IEmailService emailService,
        IScheduleService scheduleService,
        IReindeerFeeder reindeerFeeder)
    {
        public void Start()
        {
            scheduleService.OrganizeMyDay(
                scheduleService.TodaySchedule()
            );

            reindeerFeeder.FeedReindeers();
            emailService.ReadNewEmails();
            scheduleService.Continue();
        }
    }
}