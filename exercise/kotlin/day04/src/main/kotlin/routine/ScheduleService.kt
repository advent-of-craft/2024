package routine

interface ScheduleService {
    fun todaySchedule(): Schedule
    fun organizeMyDay(schedule: Schedule)
    fun continueDay()
}