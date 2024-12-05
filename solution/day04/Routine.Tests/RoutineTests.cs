using FakeItEasy;
using Routine.Tests.Fakes;
using Xunit;

namespace Routine.Tests
{
    public class RoutineTests
    {
        [Fact]
        public void StartRoutine_With_FakeItEasy()
        {
            var schedule = new Schedule();
            var emailServiceDouble = A.Fake<IEmailService>();
            var scheduleServiceDouble = A.Fake<IScheduleService>();
            var reindeerFeederDouble = A.Fake<IReindeerFeeder>();

            // Stub the method: emulate in-coming interactions
            A.CallTo(() => scheduleServiceDouble.TodaySchedule()).Returns(schedule);

            var routine = new Routine(emailServiceDouble, scheduleServiceDouble, reindeerFeederDouble);
            routine.Start();

            // We check that OrganizeMyDay is called with the result of the ScheduleService Stub
            // Mock: examine out-coming interactions are made in order and exactly Once 
            A.CallTo(() => scheduleServiceDouble.OrganizeMyDay(schedule)).MustHaveHappenedOnceExactly()
                .Then(A.CallTo(() => reindeerFeederDouble.FeedReindeers()).MustHaveHappenedOnceExactly())
                .Then(A.CallTo(() => emailServiceDouble.ReadNewEmails()).MustHaveHappenedOnceExactly())
                .Then(A.CallTo(() => scheduleServiceDouble.Continue()).MustHaveHappenedOnceExactly());
        }

        [Fact]
        public void StartRoutine_With_Manual_Fakes()
        {
            var schedule = new Schedule();
            var emailServiceFake = new EmailService();
            var scheduleServiceFake = new ScheduleService(schedule);
            var reindeerFeederFake = new ReindeerFeeder();

            var routine = new Routine(emailServiceFake, scheduleServiceFake, reindeerFeederFake);
            routine.Start();

            scheduleServiceFake.HasBeenOrganized(schedule);
            emailServiceFake.HasBeenRead();
            reindeerFeederFake.HasBeenFed();
            scheduleServiceFake.HasBeenContinued();
        }
    }
}