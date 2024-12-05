using FluentAssertions;

namespace Routine.Tests.Fakes
{
    public class EmailService : IEmailService
    {
        private bool _emailRead;
        public void ReadNewEmails() => _emailRead = true;

        public void HasBeenRead() => _emailRead.Should().BeTrue();
    }
}