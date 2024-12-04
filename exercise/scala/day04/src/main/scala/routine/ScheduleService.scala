package routine

trait ScheduleService {
  def todaySchedule(): Schedule

  def organizeMyDay(schedule: Schedule): Unit

  def continueDay(): Unit
}
