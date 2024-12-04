package routine

class Routine(
               private val emailService: EmailService,
               private val scheduleService: ScheduleService,
               private val reindeerFeeder: ReindeerFeeder
             ) {
  def start(): Unit = {
    scheduleService.organizeMyDay(scheduleService.todaySchedule())
    reindeerFeeder.feedReindeers()
    emailService.readNewEmails()
    scheduleService.continueDay()
  }
}