package routine;

public class Routine {
    private EmailService emailService;
    private ScheduleService scheduleService;
    private ReindeerFeeder reindeerFeeder;

    public Routine(EmailService emailService, ScheduleService scheduleService, ReindeerFeeder reindeerFeeder) {
        this.emailService = emailService;
        this.scheduleService = scheduleService;
        this.reindeerFeeder = reindeerFeeder;
    }

    public void start() {
        scheduleService.organizeMyDay(scheduleService.todaySchedule());
        reindeerFeeder.feedReindeers();
        emailService.readNewEmails();
        scheduleService.continueDay();
    }
}
