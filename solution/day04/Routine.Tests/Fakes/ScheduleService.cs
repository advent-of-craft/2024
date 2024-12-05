using FluentAssertions;

namespace Routine.Tests.Fakes;

public class ScheduleService(Schedule schedule) : IScheduleService
{
    private Schedule? _calledToOrganize;
    private bool _continue;

    public Schedule TodaySchedule() => schedule;

    public void OrganizeMyDay(Schedule s) => _calledToOrganize = s;

    public void Continue() => _continue = true;

    public void HasBeenOrganized(Schedule s) => _calledToOrganize.Should().Be(s);
    public void HasBeenContinued() => _continue.Should().BeTrue();
}